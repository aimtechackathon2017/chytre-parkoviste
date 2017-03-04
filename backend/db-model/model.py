# -*- coding: utf-8 -*-
"""
Redis connection pool.
@author: ycdmdj
"""

import redis

pool = redis.ConnectionPool(host='localhost', port=6379, db=0)
r = redis.Redis(connection_pool=pool)

# - tady bude listener na čtení požadavků z bt-skeneru
# - bude zapisovat do redisu ve formátu:
# {timestamp, state, car_id, pos_id, distance}

# state - místo právě obsazeno/uvolněno autem ze vzdálenosti
