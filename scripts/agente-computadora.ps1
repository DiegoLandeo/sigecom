# Agente PowerShell SIGECOM
# Captura y sincroniza datos técnicos del equipo con el sistema web

$nombreEquipo = $env:COMPUTERNAME

$ip = (Get-NetIPAddress -AddressFamily IPv4 |
        Where-Object {
            $_.IPAddress -notlike "127.*" -and
                    $_.PrefixOrigin -ne "WellKnown"
        } |
        Select-Object -First 1).IPAddress

$mac = (Get-NetAdapter |
        Where-Object { $_.Status -eq "Up" } |
        Select-Object -First 1).MacAddress

$procesador = (Get-CimInstance Win32_Processor |
        Select-Object -First 1).Name

$ramBytes = (Get-CimInstance Win32_ComputerSystem).TotalPhysicalMemory
$ramGB = [math]::Round($ramBytes / 1GB, 0)
$ram = "$ramGB GB"

$disco = Get-CimInstance Win32_LogicalDisk -Filter "DeviceID='C:'"
$discoTotalGB = [math]::Round($disco.Size / 1GB, 0)
$discoLibreGB = [math]::Round($disco.FreeSpace / 1GB, 0)

$sistemaOperativo = (Get-CimInstance Win32_OperatingSystem).Caption

$ultimaDeteccion = Get-Date -Format "yyyy-MM-dd HH:mm:ss"

$internetDetectado = Test-Connection -ComputerName "8.8.8.8" -Count 1 -Quiet

$datos = @{
    nombreEquipo = $nombreEquipo
    ip = $ip
    mac = $mac
    procesador = $procesador
    ram = $ram
    discoDuro = $discoTotalGB
    discoLibre = $discoLibreGB
    sistemaOperativo = $sistemaOperativo
    ultimaDeteccion = $ultimaDeteccion
    tieneInternet = $internetDetectado
}

$json = $datos | ConvertTo-Json

Write-Host "Enviando datos técnicos a SIGECOM..."
Write-Host $json

$respuesta = Invoke-RestMethod `
    -Uri "http://localhost:8080/computadoras/pre-registrar" `
    -Method Post `
    -Body $json `
    -ContentType "application/json"

Write-Host "Respuesta del sistema:"
Write-Host $respuesta