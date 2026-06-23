# Instalador del agente SIGECOM
# Crea la carpeta local, programa la ejecución automática y ejecuta el agente una vez

$carpetaDestino = "C:\SIGECOM"
$scriptOrigen = ".\agente-computadora.ps1"
$scriptDestino = "$carpetaDestino\agente-computadora.ps1"
$nombreTarea = "SIGECOM - Agente de Inventario"

Write-Host "Instalando agente SIGECOM..."

if (!(Test-Path $carpetaDestino)) {
    New-Item -ItemType Directory -Path $carpetaDestino | Out-Null
    Write-Host "Carpeta creada: $carpetaDestino"
} else {
    Write-Host "La carpeta ya existe: $carpetaDestino"
}

Copy-Item $scriptOrigen $scriptDestino -Force
Write-Host "Agente copiado en: $scriptDestino"

$accion = New-ScheduledTaskAction `
    -Execute "powershell.exe" `
    -Argument "-ExecutionPolicy Bypass -WindowStyle Hidden -File `"$scriptDestino`""

$disparador = New-ScheduledTaskTrigger `
    -Once `
    -At (Get-Date).AddMinutes(1) `
    -RepetitionInterval (New-TimeSpan -Minutes 30) `
    -RepetitionDuration (New-TimeSpan -Days 3650)

$configuracion = New-ScheduledTaskSettingsSet `
    -AllowStartIfOnBatteries `
    -DontStopIfGoingOnBatteries `
    -StartWhenAvailable

try {
    Register-ScheduledTask `
        -TaskName $nombreTarea `
        -Action $accion `
        -Trigger $disparador `
        -Settings $configuracion `
        -Description "Agente SIGECOM para sincronizar datos técnicos del equipo cada 30 minutos." `
        -Force `
        -ErrorAction Stop

    Write-Host "Tarea programada creada correctamente:"
    Write-Host $nombreTarea
    Write-Host "El agente se ejecutará automáticamente cada 30 minutos."
}
catch {
    Write-Host "ERROR: No se pudo crear la tarea programada."
    Write-Host $_.Exception.Message
}

Write-Host "Ejecutando agente por primera vez para pre-registrar la computadora..."

try {
    powershell.exe -ExecutionPolicy Bypass -File $scriptDestino
    Write-Host "Primera sincronización ejecutada correctamente."
}
catch {
    Write-Host "No se pudo ejecutar la primera sincronización."
    Write-Host $_.Exception.Message
}