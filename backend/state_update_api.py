import boto3
import json
import time
import uuid

db = boto3.resource('dynamodb')

pa_state = db.Table('pa_state')
pa_cur_state = db.Table('pa_cur_state')

carmac_map = db.Table('carmac_map')
placemac_map = db.Table('placemac_map')


def respond(err, res=None):
    return {
        'statusCode': '400' if err else '200',
        'body': err.message if err else res,
        'headers': {
            'Content-Type': 'application/json',
        },
    }

def map_placemac_id(mac):
    response = placemac_map.get_item(Key={'mac': mac})
    return int(response['Item']['place_id'])


def map_carmac_id(mac):
    response = carmac_map.get_item(Key={'mac': mac})
    return int(response['Item']['car_id'])


def put_status(payload):
    count = 0
    for store_status in payload:
        # obligatory arguments
        if 'place_mac' in store_status and 'available' in store_status:
            place_id = map_placemac_id(store_status['place_mac'])
            available = store_status['available']
            
            state_map = {
                    'change_id': str(uuid.uuid4()),
                    'timestamp': int(time.time()),
                    'available': available,
                    'place_id': place_id
                }

            # optional arguments
            if 'car_mac' in store_status:
                state_map['car_id'] = map_carmac_id(store_status['car_mac'])
            
            pa_state.put_item(Item=state_map)
            pa_cur_state.update_item(
                Key={
                    'place_id': place_id
                },
                UpdateExpression='SET available = :val1',
                ExpressionAttributeValues={
                    ':val1': available
                }
            )
            count = count + 1
    return {'count': count}


def lambda_handler(event, context):
    if event['method'] == 'POST':
        return respond(None, put_status(event['body']))
    else:
        return respond(ValueError('Unsupported method "{}"'.format(operation)))

