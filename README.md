# Proyecto Final - Programación 2

# 💼 Pay Sphere – Sistema de Cálculo de Nómina

## 📌 Descripción del Proyecto

**Pay Sphere** es un sistema desarrollado en **Java** que permite la gestión y cálculo de nómina de empleados, implementando una arquitectura por capas y simulando persistencia de datos mediante archivos de texto.

El sistema está diseñado para calcular salarios, deducciones y aportes patronales, además de generar reportes en formato PDF tanto para el empleado como para el patrono.

---

## 🎯 Objetivo

Desarrollar un sistema de nómina funcional que aplique buenas prácticas de ingeniería de software, incluyendo:

* Separación por capas
* Manejo de lógica de negocio
* Persistencia de datos
* Generación de reportes
* Uso de herramientas externas (PDF y correo)

---

## 🧱 Arquitectura del Sistema

El proyecto sigue una **arquitectura por capas**, estructurada de la siguiente forma:

```
📦 PaySphere
 ┣ 📂 AccesoDatos
 ┣ 📂 Entidades
 ┣ 📂 LogicaNegocio
 ┣ 📂 Presentacion
 ┗ 📂 Utilidades
```

### 🔹 Descripción de capas

* **AccesoDatos**
  Maneja la lectura y escritura de archivos (simulación de base de datos).

* **Entidades**
  Contiene las clases modelo del sistema (Empleado, Nómina, etc.).

* **LogicaNegocio**
  Implementa las reglas del negocio (cálculo de salario, deducciones, etc.).

* **Presentacion**
  Interfaz gráfica desarrollada en Java Swing.

* **Utilidades**
  Funcionalidades auxiliares como generación de PDF y envío de correos.

---

## ⚙️ Funcionalidades Principales

✔️ Gestión de empleados
✔️ Cálculo de salario bruto
✔️ Cálculo de deducciones del trabajador
✔️ Cálculo de aportes patronales
✔️ Generación de nómina

✔️ Generación de reportes en PDF:

* 📄 Reporte para el empleado
* 📄 Reporte para el patrono

✔️ Envío de reportes por correo electrónico

✔️ Persistencia de datos mediante archivos `.txt`

---

## 🔄 Flujo del Sistema

1. El usuario inicia sesión en el sistema
2. Accede al dashboard principal
3. Registra o consulta empleados
4. Genera la nómina
5. El sistema calcula automáticamente:

   * Salario bruto
   * Deducciones
   * Aportes patronales
6. Se generan dos reportes:

   * PDF para empleado
   * PDF para patrono
7. El PDF del empleado puede enviarse por correo

---

## 🧾 Estructura de Datos

El sistema utiliza archivos de texto para almacenar información como:

* Empleados
* Nómina
* Historial

Esto permite simular una base de datos sin depender de un motor externo.

---

## 🛠️ Tecnologías Utilizadas

* **Java (JDK 17+)**
* **Java Swing** (Interfaz gráfica)
* **iText / ReportLab equivalente en Java** (Generación de PDF)
* **JavaMail API** (Envío de correos)
* Persistencia con archivos `.txt`

---

## 📊 Reglas de Negocio Implementadas

* Cálculo de salario bruto:

  * Salario base + horas extra + bonificaciones

* Deducciones del trabajador:

  * SEM
  * IVM
  * Banco Popular

* Aportes patronales:

  * SEM patrono
  * IVM patrono
  * INA
  * IMAS
  * FCL
  * OPC
  * INS
  * Asignaciones familiares

---

## 📄 Generación de Reportes

### 👤 Reporte del Empleado

Incluye:

* Datos del empleado
* Período
* Salario base
* Horas extra
* Bonificaciones
* Salario bruto
* Deducciones
* Salario neto

### 🏢 Reporte del Patrono

Incluye:

* Datos del empleado
* Salario bruto
* Aportes patronales detallados
* Costo total para la empresa

---

## 📧 Envío de Correos

El sistema permite enviar automáticamente el reporte del empleado mediante configuración SMTP:

```
mail.enabled=true
smtp.host=...
smtp.port=...
smtp.user=...
smtp.password=...
```

---

## 🚀 Cómo Ejecutar el Proyecto

1. Clonar el repositorio:

```
git clone https://github.com/tu-usuario/pay-sphere.git
```

2. Abrir el proyecto en **NetBeans**

3. Ejecutar la clase principal:

```
Main.java
```

---

## 📌 Estado del Proyecto

✔️ Funcional
✔️ Cumple con requerimientos académicos
✔️ Implementa arquitectura por capas
✔️ Genera reportes profesionales

---

## 🧠 Notas Finales

Este proyecto fue desarrollado con apoyo de herramientas de inteligencia artificial, aplicando iteración, validación y mejoras manuales para cumplir con los requisitos del curso y lograr un sistema funcional y estructurado.

---
