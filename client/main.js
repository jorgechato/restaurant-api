import { check, sleep } from 'k6'
import http from 'k6/http'

import {BODY, URL} from './constants.js'


Array.prototype.random = function () {
    return this[Math.floor((Math.random()*this.length))];
}

const TABLENAME = ['t1', 't2', 't3', 't4', 't5']


export const options = {
    scenarios: {
        list: {
            executor: 'ramping-vus',
            exec: 'getOrders',
            tags: {
                endpoint: URL.GET_ORDERS+'<table-id>',
                repository: 'restauran-api',
                test: 'load',
            },
            stages: [
                {duration: '15s', target: 10},
                {duration: '20s', target: 50},
                {duration: '15s', target: 0},
            ]
        },
        create: {
            executor: 'ramping-vus',
            exec: 'createOrder',
            tags: {
                endpoint: URL.CREATE_ORDER,
                repository: 'restauran-api',
                test: 'load',
            },
            stages: [
                {duration: '15s', target: 10},
                {duration: '20s', target: 50},
                {duration: '15s', target: 0},
            ]
        },
        delete: {
            executor: 'ramping-vus',
            exec: 'deleteOrder',
            tags: {
                endpoint: URL.CREATE_ORDER,
                repository: 'restauran-api',
                test: 'load',
            },
            stages: [
                {duration: '5s', target: 5},
                {duration: '10s', target: 20},
                {duration: '5s', target: 0},
            ]
        },
    },
}

export function getOrders() {
    let res = http.get(URL.TABLE+TABLENAME.random())
    check(res, { 'Status was 200': r => r.status == res.status })
    sleep(1)
}

export function createOrder() {
    let res = http.post(
        URL.ORDER,
        JSON.stringify({
            "name": `Order-${Date.now()}`,
            "tableId": TABLENAME.random()
        })
    )
    check(res, { 'Status was 201': r => 201 == res.status })
    sleep(1)
}

export function deleteOrder() {
    sleep(2)
    let tableId = TABLENAME.random()
    let orderId = JSON.parse(http.get(URL.TABLE+tableId)["body"])[0]["id"]

    if (typeof orderId !== "undefined") {
        let res = http.del(URL.TABLE+tableId+'/order/'+orderId)
        check(res, { 'Status was 204': r => 204 == res.status })
    }
}
