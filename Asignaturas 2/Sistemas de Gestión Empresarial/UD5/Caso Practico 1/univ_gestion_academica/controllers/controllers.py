# from odoo import http


# class UnivGestionAcademica(http.Controller):
#     @http.route('/univ_gestion_academica/univ_gestion_academica', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/univ_gestion_academica/univ_gestion_academica/objects', auth='public')
#     def list(self, **kw):
#         return http.request.render('univ_gestion_academica.listing', {
#             'root': '/univ_gestion_academica/univ_gestion_academica',
#             'objects': http.request.env['univ_gestion_academica.univ_gestion_academica'].search([]),
#         })

#     @http.route('/univ_gestion_academica/univ_gestion_academica/objects/<model("univ_gestion_academica.univ_gestion_academica"):obj>', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('univ_gestion_academica.object', {
#             'object': obj
#         })

