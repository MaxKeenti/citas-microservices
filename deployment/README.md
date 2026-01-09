# Deploying to Azure Virtual Machine

This guide explains how to deploy the **Citas Microservices** application to an Azure VM.

## Prerequisites
- An active Azure subscription.
- Azure CLI installed locally (optional, but recommended).
- SSH client (Terminal, PuTTY, etc.).

## 1. Create and Configure Azure VM

1.  **Create a VM**:
    -   Go to Azure Portal > **Virtual Machines** > **Create**.
    -   **Image**: Ubuntu Server 22.04 LTS (or 24.04).
    -   **Size**: Standard_B2s (2 vCPUs, 4GB RAM) recommended for this stack.
    -   **Authentication**: SSH Public Key.

2.  **Networking**:
    -   Allow **SSH (22)**.
    -   Allow **HTTP (80)** (optional, if using a reverse proxy).
    -   **Custom Rules**: You must open specific ports for the application.
        -   Go to **Networking** (or "Connect" tab -> Networking) of your VM.
        -   Add Inbound Port Rule:
            -   **Destination Port Ranges**: `3000` (Frontend), `8080` (Web Portal, if external access needed), `8081-8084` (Microservices, usually keep closed/internal).
            -   **Protocol**: TCP.
            -   **Action**: Allow.

## 2. Prepare the VM

1.  **Connect to VM**:
    ```bash
    ssh -i <your-key.pem> azureuser@<VM_PUBLIC_IP>
    ```

2.  **Transfer Project Files**:
    On your **local machine**, navigate to the project root and copy files to the VM.
    ```bash
    # Example using SCP
    scp -i <your-key.pem> -r . azureuser@<VM_PUBLIC_IP>:~/citas-microservices
    ```
    *Alternatively, install git on the VM and clone your repository.*

## 3. Install Docker & Dependencies

We have provided a script to automate this.

1.  **Run the Setup Script** (on the VM):
    ```bash
    cd ~/citas-microservices/deployment
    chmod +x setup-vm.sh
    ./setup-vm.sh
    ```
2.  **Log out and log back in** to apply user group changes:
    ```bash
    exit
    # Re-connect via SSH
    ssh -i ...
    ```

## 4. Deploy Application

1.  **Configure Environment**:
    Navigate to the project root on the VM.
    Create a `.env` file to set your public IP.
    ```bash
    cd ~/citas-microservices
    nano .env
    ```
    Add the following line (replace with your VM's *Public IP*):
    ```env
    NEXT_PUBLIC_API_URL=http://<VM_PUBLIC_IP>:3000/api
    ```

2.  **Launch Containers**:
    ```bash
    docker compose up -d --build
    ```
    - `-d`: Detached mode (runs in background).
    - `--build`: Forces building the images (compiling Java/Node apps inside containers).

3.  **Verify**:
    -   Access the frontend at `http://<VM_PUBLIC_IP>:3000`.
    -   Check logs if needed: `docker compose logs -f`.

## Troubleshooting

-   **Frontend API Errors**: Ensure `NEXT_PUBLIC_API_URL` is set correct in `.env` and you rebuilt the containers (`docker compose up -d --build`). The browser needs to know the public IP to make API calls.
-   **Database Connection Refused**: Ensure all containers are running (`docker compose ps`). The databases take a few seconds to initialize.
-   **Ports**: Double-check Azure Networking "Inbound Port Rules".
