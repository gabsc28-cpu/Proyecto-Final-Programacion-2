# Pay Sphere – Sistema de Nómina  
### Proyecto I – Programación II  

📌 **Autores**  
- Gabriel David Solano Chaves  
- Fiorella Pereira  
- Mauricio Azofeifa Cervantes  
- Cristel Quesada Monge  

📅 **Periodo:** I Cuatrimestre 2026  

---

## Descripción del Proyecto  

**Pay Sphere** es una aplicación de escritorio desarrollada en Java que automatiza la gestión de nómina empresarial.  

El sistema permite:  
- Autenticación de usuarios  
- Gestión de empleados  
- Cálculo de planilla (Costa Rica)  
- Generación de reportes en PDF  
- Envío de comprobantes por correo electrónico  

---

## Objetivo  

Desarrollar un sistema de nómina con arquitectura por capas que permita:  
- Automatizar procesos administrativos  
- Mantener un código organizado, escalable y mantenible  
- Simular un entorno empresarial real  

---

## Arquitectura del Sistema  

El sistema está diseñado bajo una **arquitectura por capas**:

Presentación → Lógica de Negocio → Acceso a Datos → Entidades


### 📦 Capas

- **Presentación**
  - Interfaces gráficas (Swing)
  - Interacción con el usuario  

- **Lógica**
  - Reglas de negocio
  - Cálculos de nómina  

- **Datos**
  - Lectura y escritura en archivos de texto  

- **Entidades**
  - Modelos del sistema  

- **Excepciones**
  - Manejo de errores personalizados  

- **Utilidades**
  - Validaciones, hashing, formatos y helpers  

---

## 🔄 Flujo del Sistema  

1. `MainApp` inicia la aplicación  
2. Se muestra `LoginFrame`  
3. Se validan credenciales con `AutenticacionService`  
4. Se accede al `Dashboard`  
5. El usuario puede:
   - Gestionar empleados  
   - Generar nómina  
   - Crear reportes PDF  
   - Enviar comprobantes por correo  

---

## 🧩 Paquetes Principales  

### 🖥️ Presentación
- `MainApp`
- `LoginFrame`
- `DashboardFrame`
- `PanelEmpleados`
- `PanelNomina`
- `PanelReportes`

### ⚙️ Lógica
- `AutenticacionService`
- `EmpleadoService`
- `ServicioNomina`
- `CalculadoraNominaCostaRica`
- `ReportePdfService`
- `CorreoNominaService`

### 💾 Datos
- `ArchivoDAO`
- `ArchivoEmpleadoDAO`
- `ArchivoUsuarioDAO`

### 📊 Entidades
- `Empleado`
- `Usuario`
- `Nomina`
- `DetalleNomina`
- `ResumenPatronal`

### ⚠️ Excepciones
- `ArchivoInvalidoException`
- `AutenticacionFallidaException`
- `ValidacionException`
- `CorreoException`

### 🛠️ Utilidades
- `RutaAplicacion`
- `ArchivoUtil`
- `HashUtil`
- `ValidadorUtil`
- `FormatoUtil`
- `TemaVisual`

---

## 🧪 Tecnologías Utilizadas  

- Java  
- Swing  
- FlatLaf  
- iText  
- JavaMail  
- Archivos de texto  

---

## ▶️ Ejecución del Proyecto  

1. Abrir **NetBeans 17**  
2. Ir a `File > Open Project`  
3. Seleccionar la carpeta del proyecto  
4. Verificar clase principal:  

data/
│
├── usuarios.txt
├── empleados.txt
└── config-correo.properties


---

## 📚 Librerías  

- flatlaf-3.4.1.jar  
- itextpdf-5.5.13.3.jar  
- javax.mail-1.6.2.jar  
- activation-1.1.1.jar  

---

## ⚠️ Manejo de Errores  

- `ArchivoInvalidoException`  
- `AutenticacionFallidaException`  
- `ValidacionException`  
- `CorreoException`  

---

## 📖 Documentación  

- Javadoc en clases principales  
- `package-info.java` por paquete  
- Diagrama de arquitectura (PDF aparte)  

---

## 💡 Valor del Proyecto  

✔ Automatización de planilla  
✔ Arquitectura por capas  
✔ Generación de reportes PDF  
✔ Envío de correos  
✔ Código mantenible y escalable  

---

## 🏁 Conclusión  

**Pay Sphere** demuestra una implementación sólida de buenas prácticas en desarrollo de software, aplicando arquitectura por capas y separación de responsabilidades en un sistema funcional.

---

## ⭐ Mejoras Futuras  

- Migración a base de datos SQL  
- API REST  
- Versión web  
- Dashboard con métricas  
