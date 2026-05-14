# from odoo import models, fields, api


# class univ_gestion_academica(models.Model):
#     _name = 'univ_gestion_academica.univ_gestion_academica'
#     _description = 'univ_gestion_academica.univ_gestion_academica'

#     name = fields.Char()
#     value = fields.Integer()
#     value2 = fields.Float(compute="_value_pc", store=True)
#     description = fields.Text()
#
#     @api.depends('value')
#     def _value_pc(self):
#         for record in self:
#             record.value2 = float(record.value) / 100

