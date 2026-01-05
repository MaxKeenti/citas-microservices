package mx.ipn.upiicsa.web.appointment.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import mx.ipn.upiicsa.web.appointment.client.HResourcesClient;
import mx.ipn.upiicsa.web.appointment.dto.HorarioDto;
import mx.ipn.upiicsa.web.appointment.model.Cita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/availability")
@Produces(MediaType.APPLICATION_JSON)
public class AvailabilityResource {

    @Inject
    @RestClient
    HResourcesClient hResourcesClient;

    @GET
    public List<LocalTime> getAvailability(
            @QueryParam("employeeId") Integer employeeId,
            @QueryParam("date") String dateString,
            @QueryParam("serviceDuration") Integer serviceDuration) {

        if (employeeId == null || dateString == null || serviceDuration == null) {
            return new ArrayList<>(); // Or BadRequest
        }

        // 1. Parse Date
        LocalDate date = LocalDate.parse(dateString);
        int dayOfWeek = date.getDayOfWeek().getValue(); // 1=Mon, 7=Sun

        // 1.1 Check Days Off
        try {
            List<mx.ipn.upiicsa.web.appointment.dto.DiaDescansoDto> daysOff = hResourcesClient.getDaysOff(employeeId);
            boolean isDayOff = daysOff.stream().anyMatch(d -> d.fecha.equals(date));
            if (isDayOff) {
                return new ArrayList<>(); // Day off, no availability
            }
        } catch (Exception e) {
            // Log error but continue? Or fail safe?
            // For now, if we can't reach hresources to check days off, proceed (or maybe
            // should handle better)
            System.err.println("Error fetching days off: " + e.getMessage());
        }

        // 2. Fetch Employee Schedule
        List<HorarioDto> schedules = hResourcesClient.getHorarios(employeeId);
        // Find schedule for this day
        // Assuming database uses 1=Mon..7=Sun convention or similar.
        // HResources seeds: 1=Lunes.
        HorarioDto todaySchedule = schedules.stream()
                .filter(h -> h.idDia == dayOfWeek)
                .findFirst()
                .orElse(null);

        if (todaySchedule == null) {
            return new ArrayList<>(); // Not working today
        }

        // 3. Generate all possible slots
        List<LocalTime> potentialSlots = new ArrayList<>();
        LocalTime current = todaySchedule.horaInicio;
        LocalTime end = todaySchedule.horaFin;

        while (current.plusMinutes(serviceDuration).isBefore(end) || current.plusMinutes(serviceDuration).equals(end)) {
            potentialSlots.add(current);
            current = current.plusMinutes(30); // 30 min intervals
        }

        // 4. Fetch Existing Appointments
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<Cita> existingAppointments = Cita.list("idEmpleado = ?1 and fechaHora >= ?2 and fechaHora <= ?3",
                employeeId, startOfDay, endOfDay);

        // 5. Filter Collisions
        return potentialSlots.stream().filter(slot -> {
            LocalDateTime slotStart = date.atTime(slot);
            LocalDateTime slotEnd = slotStart.plusMinutes(serviceDuration);

            for (Cita appt : existingAppointments) {
                // appt start/end
                LocalDateTime apptStart = appt.fechaHora;
                LocalDateTime apptEnd = appt.fechaHora.plusMinutes(appt.duracion);

                // Check overlap
                if (slotStart.isBefore(apptEnd) && slotEnd.isAfter(apptStart)) {
                    return false; // Collision
                }
            }
            return true;
        }).collect(Collectors.toList());
    }
}
