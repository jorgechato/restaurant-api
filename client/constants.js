const base_url = __ENV.API_URL || 'http://localhost:8080'

export const URL = {
  ORDER: base_url+'/order',
  TABLE: base_url+'/table/',
};
