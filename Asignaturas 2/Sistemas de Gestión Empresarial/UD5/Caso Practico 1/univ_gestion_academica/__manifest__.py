# -*- coding: utf-8 -*-
{
    'name': "Gestión Académica Universitaria",
    'summary': "Módulo a medida para la gestión integral de Cursos y Asignaturas",
    'description': """
        Módulo desarrollado para el departamento de informática de la Universidad.
        Este desarrollo proporciona:
        - Un modelo relacional para el registro de la oferta formativa (Cursos).
        - Un catálogo detallado de Asignaturas.
        - Relación estructurada N:M (Muchos a Muchos) entre cursos y asignaturas.
        - Generación automática de informes en formato PDF (QWeb) con el plan de estudios.
    """,
    'author': "Departamento de Informática - Equipo de Desarrollo",
    'category': 'Education',
    'version': '1.0',
    
    # Dependencias base del sistema ERP
    'depends': ['base'],
    
    # Archivos que Odoo debe cargar (El orden de declaración es estricto)
    'data': [
        'security/ir.model.access.csv',  # 1º: Permisos y accesos
        'views/menu_views.xml',          # 2º: Estructura de menús principales
        'views/asignatura_views.xml',    # 3º: Vistas del modelo Asignatura
        'views/curso_views.xml',         # 4º: Vistas del modelo Curso
        'reports/curso_report.xml',      # 5º: Plantillas de impresión QWeb

        # Movemos el archivo de demo aquí para que Odoo lo instale obligatoriamente
        'demo/demo_data.xml',
    ],
    
    # Datos inyectados automáticamente para realizar pruebas funcionales
    'demo': [
      
    ],
    
    # Variables de control de la aplicación
    'installable': True,
    'application': True,
    'auto_install': False,
}