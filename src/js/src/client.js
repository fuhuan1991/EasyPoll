import fetch from 'unfetch';

const baseUrl = 'http://easypoll.us-east-2.elasticbeanstalk.com/';

// check the status of all sort of response
const checkStatus = response => {
    if (response.ok) {
      return response;
    } else {
        let error = new Error(response.statusText);
        error.response = response;
        response.json().then(e => {
          error.error = e;
        });
        return Promise.reject(error);
      }
  }

// create a new Poll
export const createPoll = data => fetch(baseUrl + 'api/create', {
    headers: {
      'Content-Type': 'application/json',
    },
    method: 'POST',
    body: JSON.stringify(data),
}).then(checkStatus)
  .then(response => {
    return response.text();
  }, e => {
    return e;
  }
);

