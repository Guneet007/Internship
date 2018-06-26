# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin

# Register your models here.

from .models import Product, NutritionalInfo, Ingredients, Order, User

# Register your models here.
admin.site.register(Product)
admin.site.register(NutritionalInfo)
admin.site.register(Ingredients)
admin.site.register(Order)
admin.site.register(User)
