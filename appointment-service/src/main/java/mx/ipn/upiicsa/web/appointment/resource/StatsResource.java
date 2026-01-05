package mx.ipn.upiicsa.web.appointment.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import mx.ipn.upiicsa.web.appointment.model.Cita;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Path("/stats")
@Produces(MediaType.APPLICATION_JSON)
public class StatsResource {

    @GET
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();

        // Total Appointments
        stats.put("totalAppointments", Cita.count());

        // Appointments Today
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        stats.put("appointmentsToday", Cita.count("fechaHora >= ?1 and fechaHora <= ?2", startOfDay, endOfDay));

        // Pending Confirmations (Status 1 = Agendada)
        stats.put("pendingConfirmations", Cita.count("estado.id = 1"));

        return stats;
    }
}
