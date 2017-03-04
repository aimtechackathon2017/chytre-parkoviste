# -*- coding: utf-8 -*-
"""
ORM model for raw db.
@author: ycdmdj
"""

from django.db import models

class PlaceEvent(models.Model):
    timestamp = models.DateField(auto_now=True)
    available = models.BooleanField((max_length=128)
    place_id = models.UUIDField(default=uuid.uuid4, editable=False)
    car_id = models.UUIDField(default=uuid.uuid4, editable=False)
    distance = models.DecimalField(..., max_digits=4, decimal_places=2)

    class Meta(object):
        ordering = ['timestamp']

    def __str__(self):
        return "place {0} {1}".format(self.place_id, "available" if self.available else "occupied")
