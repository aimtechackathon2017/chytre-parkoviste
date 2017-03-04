import boto3

db = boto3.resource('dynamodb')

pa_state = db.Table('pa_state')

def respond(err, res=None):
    return {
        'statusCode': '400' if err else '200',
        'body': err.message if err else res,
        'headers': {
            'Content-Type': 'application/json',
        },
    }

def get_journal():
    response = pa_state.scan()
    return response['Items']

def lambda_handler(event, context):
    if event['method'] == 'GET':
        return respond(None, get_journal())
    else:
        return respond(ValueError('Unsupported method "{}"'.format(operation)))

