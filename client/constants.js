Array.prototype.random = function () {
  return this[Math.floor((Math.random()*this.length))];
}


const table_name = ['t1', 't2', 't3', 't4', 't5']

const base_url = 'http://localhost:8080'

export const URL = {
  CREATE_ORDER: __ENV.API_URL || base_url+'/order',
//`DELETE` /table/{table:id}/order/{order:id} (204)
  //DELETE_ORDER: __ENV.API_URL || base_url+'/table',
//`GET` /table/{table:id}/order/{order:id}
  //GET_ORDER: __ENV.API_URL || base_url+'',
  GET_ORDERS: __ENV.API_URL || base_url+'/table/'+table_name.random(),
};

export const BODY = {
  EMPTY: JSON.stringify({}),

  CREATE_ORDER: {
    "name": ,
    "tableId": table-id
  }
}
