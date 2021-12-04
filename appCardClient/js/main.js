class CardManager {
  API_URL = 'http://127.0.0.1:8080/v1/';

  constructor() {
    this.getHTMLElements();
    this.addBindListeners();
    this.addListeners();
  }

  getHTMLElements() {
    this.createCardsButton = document.getElementById('createCardsButton');
    this.shuffleCardsButton = document.getElementById('shuffleCardsButton');
    this.discardCardsButton = document.getElementById('discardCardsButton');
    this.putDiscartButton = document.getElementById('putDiscardCardsButton');
    this.mescartes = document.getElementById('mescartes');
    this.madefausse = document.getElementById('defausse')
    this.message = document.getElementById('message');
    this.showButton = document.getElementById('showGameButton');
    this.hideButton = document.getElementById('hideGameButton');
    this.cardsList = document.getElementById('cardsList');
    this.discartList = document.getElementById('discartList');
    this.getCardsButton = document.getElementById('getCardsButton');
    this.cardIdInput = document.getElementById('cardIdInput');
    this.cardNb = document.getElementById('cardNb1');
    this.messageErrorCardId = document.getElementById('messageErrorCardId');
    this.cardsPanel = document.getElementById('cardsPanel');
    this.deckId = document.getElementById('deckId');
    this.deleteCardsButton = document.getElementById('deleteCardsButton');
    this.cardsForm = document.getElementById('cardsForm');
  }

  addListeners() {
    this.createCardsButton.addEventListener('click', this.createCardsListener, true);
    this.getCardsButton.addEventListener('click', this.getCardsListener, true);
  }

  removeListeners() {
    this.createCardsButton.removeEventListener('click', this.createCardsListener, true);
    this.getCardsButton.removeEventListener('click', this.getCardsListener, true);
  }

  addBindListeners() {
    this.createCardsListener = this.createCards.bind(this);
    this.getCardsListener = this.getCards.bind(this);
    this.deleteCardsListener = this.deleteCards.bind(this);
    this.shuffleCardsListener = this.shuffleCards.bind(this);
    this.discartCardsListener = this.discartCards.bind(this);
    this.putDiscartedCardListener = this.putDiscartedCard.bind(this);
    this.showButtonListener = this.showCards.bind(this);
    this.hideButtonListener = this.showEntete.bind(this);
  }


  createCards(ev) {
    ev.stopPropagation();
    fetch(this.API_URL + 'cardTab?nb=' + this.cardNb.value, {
      method: 'post',
      mode: "cors"
    })
      .then(response => {
        if (!response.ok) {
          throw new Error()
        }
        return response.json();
      })
      .then(json => {
        this.cards = json;
        this.showEntete();
        this.message.innerHTML = 'Deck ' + this.cards.cardTabId + ' créé !<br>';
      })
      .catch(err => console.log(err))
    ;
  }

  deleteCards(ev) {
    ev.stopPropagation();
    fetch(this.API_URL + 'cardTab?id=' + this.cards.cardTabId, {
      method: 'DELETE',
      mode: "cors"
    })
      .then(response => {
        if (!response.ok || response.status !== 200) {
          throw new Error();
        }
      })
      .then(_ => {
        this.message.innerHTML = 'Deck ' + this.cards.cardTabId + ' supprimé !<br>';
        this.cards = null;
        this.hideCards();
      })
    ;
  }

  shuffleCards(ev) {
    ev.stopPropagation();

    fetch(this.API_URL + 'cardTab/shuffle?id=' + this.cards.cardTabId, {
      method: 'POST',
      mode: "cors"
    })
      .then(response => {
        if (!response.ok || response.status !== 200) {
          throw new Error();
        }
        return response.json()
      })
      .then(cards => {
        this.cards = cards;
        this.showEntete();
        this.message.innerHTML = 'Deck ' + this.cardNb.value + ' mélangé !<br>';
      }).catch(() => {
      alert("No cards in your package");
    })
    ;
  }

  discartCards(ev) {
    ev.stopPropagation();
    fetch(this.API_URL + 'cardTab/discardFirstCard?id=' + this.cards.cardTabId, {
      method: 'POST',
      mode: "cors"
    })
      .then(response => {
        if (!response.ok || response.status !== 200) {
          throw new Error();
        }
        return response.json()
      })
      .then(cards => {
        this.cards = cards;
        this.showEntete();
        this.message.innerHTML = 'la premiere carte est mise dans la défausse !';
      }).catch(() => {
      alert("No cards in your package");
    });
  }

  putDiscartedCard(ev) {
    ev.stopPropagation();
    console.log(this.cards);
    fetch(this.API_URL + 'cardTab/putDiscardCardOnGame?id=' + this.cards.cardTabId, {
      method: 'POST',
      mode: "cors"
    })
      .then(response => {
        if (!response.ok || response.status !== 200) {
          throw new Error();
        }
        return response.json()
      })
      .then(cards => {
        this.cards = cards;
        this.showEntete();
        this.message.innerHTML = 'la premiere carte est remise dans le jeu !';
      }).catch(() => {
      alert("No discarted cards");
    })
    ;
  }


  getCards(ev) {
    ev.stopPropagation();
    const id = this.cardIdInput.value;
    if (isNaN(id)) {
      this.messageErrorCardId.innerHTML = 'ID incorrect !<br>'
      return;
    }

    fetch(this.API_URL + 'cardTab?id=' + id, {
      method: 'get',
      mode: "cors"
    })
      .then(response => {
        if (!response.ok || response.status !== 200) {
          throw new Error();
        }
        return response.json();
      })
      .then(json => {
        this.cards = json;
        this.showEntete();
        this.message.innerHTML = 'Deck ' + id + ' trouvé !<br>';
      })
      .catch(() => {
        this.messageErrorCardId.innerHTML = 'ID incorrect !<br>';
      })
    ;
  }


  hideCards() {
    this.addListeners();
    this.hideButton.removeEventListener('click', this.hideButtonListener, true)
    this.deleteCardsButton.removeEventListener('click', this.deleteCardsListener, true);
    this.shuffleCardsButton.removeEventListener('click', this.shuffleCardsListener, true);
    this.discardCardsButton.removeEventListener('click', this.discartCardsListener, true);
    this.putDiscartButton.removeEventListener('click', this.putDiscartedCardListener, true);

    this.cardsForm.style.display = 'block';
    this.cardsPanel.style.display = 'none';
    this.cardIdInput.value = '';
    this.messageErrorCardId.innerHTML = '';
    this.cardsList.innerHTML = '';
    this.discartList.innerHTML = '';
  }

  showEntete() {
    this.removeListeners();

    this.hideButton.addEventListener('click', this.hideButtonListener, true);
    this.showButton.addEventListener('click', this.showButtonListener, true);
    this.deleteCardsButton.addEventListener('click', this.deleteCardsListener, true);
    this.shuffleCardsButton.addEventListener('click', this.shuffleCardsListener, true)
    this.discardCardsButton.addEventListener('click', this.discartCardsListener, true);
    this.putDiscartButton.addEventListener('click', this.putDiscartedCardListener, true)
    this.cardsForm.style.display = 'none';
    this.cardsPanel.style.display = 'block';
    this.deckId.innerText = this.cards.cardTabId;
    this.cardsList.innerHTML = '';
    this.discartList.innerHTML = '';
    this.mescartes.style.display = 'none';
    this.madefausse.style.display = 'none';
  }

  showCards() {
    this.removeListeners();
    this.hideButton.addEventListener('click', this.hideButtonListener, true);
    this.showButton.addEventListener('click', this.showButtonListener, true);
    this.deleteCardsButton.addEventListener('click', this.deleteCardsListener, true);
    this.shuffleCardsButton.addEventListener('click', this.shuffleCardsListener, true)
    this.discardCardsButton.addEventListener('click', this.discartCardsListener, true);
    this.putDiscartButton.addEventListener('click', this.putDiscartedCardListener, true)
    let i = 0;
    this.cardsForm.style.display = 'none';
    this.cardsPanel.style.display = 'block';
    this.deckId.innerText = this.cards.cardTabId;
    this.cardsList.innerHTML = '';
    this.message.innerHTML = '';
    this.mescartes.style.display = 'block';
    this.madefausse.style.display = 'block';
    this.cards.mescartes.forEach(card => {
      let cardElement = document.createElement('li');
      cardElement.innerText = card;

      if (card.includes('coeur') || card.includes('carreau')) {
        cardElement.classList.add('red');
        cardElement.innerText = cardElement.innerText + ' ♡'
      }
      i = i + 1;
      setTimeout(() => {
        this.cardsList.appendChild(cardElement);
      }, 100 * i)
    })
    this.discartList.innerHTML = '';
    this.cards.defausse.forEach(card => {
      let cardElementDefausse = document.createElement('li');
      cardElementDefausse.innerText = card;
      if (card.includes('coeur') || card.includes('carreau')) {
        cardElementDefausse.classList.add('red');
      }
      i = i + 1;

      setTimeout(() => {
        this.discartList.appendChild(cardElementDefausse);
      }, 100 * i)

    })
  }
}



new CardManager()
