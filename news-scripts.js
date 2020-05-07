const app = document.getElementById('root');

const logo = document.createElement('img');
logo.src = 'logo.png';

const container = document.createElement('div');
container.setAttribute('class', 'container');

app.appendChild(logo);
app.appendChild(container);

var request = new XMLHttpRequest();
request.withCredentials = true;
request.open('GET', 'https://x9lx3yf7sr:h3yas5ptyy@newsobotics-4731548659.us-east-1.bonsaisearch.net:443/news/_search?q=Top%20Stories&from=0&size=100', true);
request.onload = function () {

  // Begin accessing JSON data here
  var data = JSON.parse(this.response);
  data = data.hits.hits;
  if (request.status >= 200 && request.status < 400) {
    data.forEach(movie => {
      var newsItem = movie._source;
      const card = document.createElement('div');
      card.setAttribute('class', 'card');

      const h1 = document.createElement('h1');
      h1.textContent = newsItem.title;

      const p = document.createElement('p');
      p.textContent = newsItem.link;

      container.appendChild(card);
      card.appendChild(h1);
      card.appendChild(p);
    });
  } else {
    const errorMessage = document.createElement('marquee');
    errorMessage.textContent = `Gah, it's not working!`;
    app.appendChild(errorMessage);
  }
}

request.send();
