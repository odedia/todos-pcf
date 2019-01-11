import axios from 'axios'  

//The updated code is using PCF route mappings instead of port binding.
//const SERVER_URL = 'http://localhost:9000';  
  
const instance = axios.create({  
  baseURL: 'https://' + window.location.hostname + '/api',
  timeout: 1000  
});  
  
export default {  
  // (C)reate  
  createNew: (text, completed) => instance.post('todos', {title: text, completed: completed}),  
  // (R)ead  
  getAll: () => instance.get('todos', {  
    transformResponse: [function (data) {  
      return data ? JSON.parse(data)._embedded.todos : data;  
    }]  
  }),  
  // (U)pdate  
  updateForId: (id, text, completed) => instance.put('todos/'+id, {title: text, completed: completed}),  
  // (D)elete  
  removeForId: (id) => instance.delete('todos/'+id)  
}
