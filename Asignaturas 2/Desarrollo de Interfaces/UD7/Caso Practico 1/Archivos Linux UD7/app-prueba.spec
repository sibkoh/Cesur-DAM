Name:           app-prueba
Version:        1.0
Release:        1%{?dist}
Summary:        Aplicación Java de prueba (JDK 21) para caso práctico UD7
License:        Educativa
Source0:        app-prueba-%{version}.tar.gz
BuildArch:      noarch
# CAMBIO CLAVE: Requerimos la versión 21 de OpenJDK para que coincida con tu compilación
Requires:       java-21-openjdk

%description
Aplicación de escritorio Java compilada con JDK 21 LTS. Este paquete
ha sido creado como parte del despliegue de software en entornos Red Hat.

%prep
%setup -q -c

%build
# No requiere compilación de código nativo

%install
# Creamos la estructura en la "maqueta" (buildroot)
mkdir -p %{buildroot}/opt/app-prueba
mkdir -p %{buildroot}/usr/bin

# Copiamos el JAR que extrajimos del tar.gz en la fase %prep
cp AppPruebaRPM.jar %{buildroot}/opt/app-prueba/

# Creamos el lanzador que invoca a la JVM 21
cat <<EOF > %{buildroot}/usr/bin/app-prueba
#!/bin/bash
java -jar /opt/app-prueba/AppPruebaRPM.jar
EOF

chmod 755 %{buildroot}/usr/bin/app-prueba

%files
/opt/app-prueba/AppPruebaRPM.jar
/usr/bin/app-prueba

%changelog
* Fri Apr 24 2026 Alumno SysAdmin <alumno@practica.local> - 1.0-1
- Versión adaptada para entorno de ejecución Java 21 LTS.
