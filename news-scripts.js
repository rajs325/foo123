
function fetch(term) {
  const app = document.getElementById('root');

  //const logo = document.createElement('img');
  //logo.setAttribute('class', 'center');
  //logo.setAttribute('style', 'height:20%;padding-bottom:10px;');
  //logo.src = 'logo.png';

  const container = document.createElement('div');
  container.setAttribute('class', 'container-fluid');

  const card_columns = document.createElement('div');
  card_columns.setAttribute('class', 'card-columns');
  card_columns.setAttribute('style', 'padding:10px 20px');

  //app.appendChild(logo);
  app.appendChild(container);
  container.appendChild(card_columns);

  var request = new XMLHttpRequest();
  request.withCredentials = true;
  request.open('GET', 'https://x9lx3yf7sr:h3yas5ptyy@newsobotics-4731548659.us-east-1.bonsaisearch.net:443/news/_search?q=predicts may might prediction will future&from=0&size=300', true);
  request.onload = function () {

    // Begin accessing JSON data here
    var data = JSON.parse(this.response);
    data = data.hits.hits;
    if (request.status >= 200 && request.status < 400) {
      data.forEach(movie => {
        var newsItem = movie._source;
        const card = document.createElement('div');
        card.setAttribute('class', 'card border-top-0 border-left-0 border-right-0 border-3');

        //const h1 = document.createElement('h5');
        //h1.textContent = newsItem.title;

        const h4 = document.createElement('h4');
        const ahref = document.createElement('a');
        ahref.setAttribute('href', newsItem.link);
        ahref.textContent = ' ' + newsItem.title;
        h4.appendChild(ahref);


        if(newsItem.mediaURL != 'null') {
          const img = document.createElement('img');
          img.setAttribute('class', 'card-image-top');
          img.setAttribute('style', 'width:100%;padding-bottom:10px;');
          img.src = newsItem.mediaURL;
          card.appendChild(img);
        }

        const source = document.createElement('span');
        source.setAttribute('class', 'badge badge-primary');
        source.textContent = newsItem.source;

        const ago = document.createElement('span');
        ago.setAttribute('class', 'badge ago');
        ago.textContent = newsItem.ago;

        const p1 = document.createElement('p');
        p1.appendChild(source);
        p1.appendChild(ago);

        const p2 = document.createElement('p');
        p2.setAttribute('class', 'card-text h5');
        p2.textContent = newsItem.description + '\n';
        const br = document.createElement('br');

        card.appendChild(h4);
        card.appendChild(p1);
        card.appendChild(p2);
        card.appendChild(br);
        card_columns.appendChild(card);
      });
    } else {
      const errorMessage = document.createElement('marquee');
      errorMessage.textContent = `Gah, it's not working!`;
      app.appendChild(errorMessage);
    }
  }

  request.send();
}