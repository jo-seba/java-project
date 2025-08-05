local items = redis.call('LRANGE', KEYS[1], 1, -1)
redis.call('LTRIM', KEYS[1], 0, 0)
return items