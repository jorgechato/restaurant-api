# Restaurant API

## Deploy

```bash
# I would like to change the image creation to a multistage docker in order to reduce the image size as much as
# possible. But since I'm not that versed on scala I decided to use an automatic packager.
$ sbt docker:publishLocal
# To deploy it on k8s (prepared for the k8s docker-desktop)
$ kubectl apply -f deploy/k8s.yml
# To use it in plain docker
$ docker run --rm -p 8080:8080 restaurant-api:0.0.1-SNAPSHOT
```

## HTTP method allowed

|  Method  | Description                               |
| -------- |-------------------------------------------|
| `GET`    | Get a resource or get a list of resources |
| `POST`   | Create a resource                         |
| `DELETE` | Delete a resource                         |

## Code Response

|  Code  |                         Description                          |
| ------ | ------------------------------------------------------------ |
| `200`  | Success                                                      |
| `201`  | Success - new resource build.                                |
| `204`  | Success - there is not a resource to response                |
| `400`  | Bad Request - i.e. invalid request                           |
| `401`  | Unauthorized - no token for this resource                    |
| `404`  | Not Found - resource doesn't exist                           |
| `422`  | Unprocessable Entity - i.e. validation error                 |
| `429`  | Request exceeded the limit                                   |
| `500`  | Server error                                                 |
| `503`  | Server holidays                                              |

## Create a new order

`POST` /order (201)

```json
{
    "name": name,
    "tableId": table-id
}
```

Response

```json
{
    "id": "10b50670-e10e-4b30-9adb-d7034ee4bbc1",
    "name": "test",
    "tableId": "t1",
    "orderAt": timestamp,
    "minutesToReady": 7
}
```

## Get a order by order & table id

`GET` /table/{table:id}/order/{order:id}

Response

```json
{
    "id": "10b50670-e10e-4b30-9adb-d7034ee4bbc1",
    "name": "test",
    "tableId": "t1",
    "orderAt": timestamp,
    "minutesToReady": 7
}
```

## Delete a order by order & table id

`DELETE` /table/{table:id}/order/{order:id} (204)

## Get a list of items by table id

`GET` /table/{table:id}

Response

```json
[
    {
        "id": "10b50670-e10e-4b30-9adb-d7034ee4bbc1",
        "name": "test",
        "tableId": "t1",
        "orderAt": timestamp,
        "minutesToReady": 7
    },
    {
        "id": "9dc99de5-6596-4b26-a11a-494ee5604a41",
        "name": "test2",
        "tableId": "t1",
        "orderAt": timestamp,
        "minutesToReady": 7
    }
    ...
]
```

## Client

We are using `k6` as a client. Grafana k6 is an open-source load testing tool where we can set multiple sync/async clients.

## Install

```bash
$ cd client
$ npm install
```

## Run

```bash
$ npm start
```
