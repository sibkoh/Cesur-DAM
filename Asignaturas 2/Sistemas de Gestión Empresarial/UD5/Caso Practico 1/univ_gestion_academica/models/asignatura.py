# -*- coding: utf-8 -*-
# Importamos la librería base de odoo. 'models' sirve para crear la clase y 'fields' para definir las columnas.
from odoo import models, fields

class UnivAsignatura(models.Model):
    # _name es obligatorio. Será el nombre de la tabla en PostgreSQL (sustituyendo el punto por guion bajo: univ_asignatura)
    _name = 'univ.asignatura'
    _description = 'Asignatura Universitaria'

    # Definimos los campos (columnas de la base de datos)
    # fields.Char es para textos cortos. required=True hace que sea obligatorio rellenarlo.
    name = fields.Char(string='Nombre de la Asignatura', required=True, help='Ej: Base de Datos Relacionales')
    
    # fields.Text es para textos largos o párrafos.
    description = fields.Text(string='Descripción del temario')