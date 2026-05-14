# -*- coding: utf-8 -*-
from odoo import models, fields

class UnivCurso(models.Model):
    _name = 'univ.curso'
    _description = 'Curso Académico'

    name = fields.Char(string='Nombre del Curso', required=True)
    
    # fields.Html permite que en la interfaz aparezca un editor de texto enriquecido (negritas, cursivas, listas)
    description = fields.Html(string='Detalles del Programa')
    
    # Campos de fecha
    fecha_inicio = fields.Date(string='Fecha de Inicio', required=True)
    fecha_fin = fields.Date(string='Fecha de Finalización')
    
    # RELACIÓN MUCHOS A MUCHOS:
    # 'comodel_name' apunta al modelo con el que nos relacionamos (univ.asignatura).
    # Odoo creará automáticamente una tabla intermedia en la base de datos para gestionar esta relación.
    asignatura_ids = fields.Many2many(
        comodel_name='univ.asignatura',
        string='Asignaturas Matriculadas'
    )