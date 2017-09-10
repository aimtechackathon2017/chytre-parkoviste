import boto3
import json

db = boto3.resource('dynamodb')

pa_cur_state = db.Table('pa_cur_state')


def respond(err, res=None):
    return {
        'statusCode': '400' if err else '200',
        'body': err.message if err else json.dumps(res),
        'headers': {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
    }


def get_status():
    response = pa_cur_state.scan()
    statuses = response['Items']
    
    result = list()
    for status in statuses:
        result.append({
            'place_id': int(status['place_id']),
            'available': status['available']
        })
    return result


def lambda_handler(event, context):
    return respond(None, get_status())

