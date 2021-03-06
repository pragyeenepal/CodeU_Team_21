/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
    window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
    document.getElementById('page-title').innerText = parameterUsername;
    document.title = parameterUsername + ' - User Page';
}

/**
 *  Shows the message from other users if the user is logged in.
 */

function showMessageFormIfLoggedIn() {
    fetch('/login-status')
        .then((response) => {
            return response.json();
        })
        .then((loginStatus) => {
            if (loginStatus.isLoggedIn) {
                const messageForm = document.getElementById('message-form');
                messageForm.action = '/messages?recipient=' + parameterUsername;
                messageForm.classList.remove('hidden');
                fetchImageUploadUrlAndShowForm();
            }
        });
}

/**
 * Shows the message form if the user is logged in and viewing their own page.
 */
function showMessageFormIfViewingSelf() {
    fetch('/login-status')
        .then((response) => {
            return response.json();
        })
        .then((loginStatus) => {
            if (loginStatus.isLoggedIn &&
                loginStatus.username == parameterUsername) {
                const messageForm = document.getElementById('message-form');
                console.log(messageForm);
                messageForm.action = '/messages?recipient=' + parameterUsername;
                messageForm.classList.remove('hidden');
                fetchImageUploadUrlAndShowForm();
            }
        });
}

function fetchImageUploadUrlAndShowForm() {
  fetch('/image-upload-url')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('message-form');
        messageForm.action = imageUploadUrl;
        messageForm.classList.remove('hidden');
      });
=======
    fetch('/image-upload-url')
        .then((response) => {
            return response.text();
        })
        .then((imageUploadUrl) => {
            const messageForm = document.getElementById('message-form');
            messageForm.action = imageUploadUrl;
            messageForm.classList.remove('hidden');
        });
    //for translating purpose
    const parameterLanguage = urlParams.get('language');
    let url = '/messages?user=' + parameterUsername;
    if (parameterLanguage) {
        url += '&language=' + parameterLanguage;
    }
}

/** Fetches messages and add them to the page. */
function fetchMessages() {
    const url = '/messages?user=' + parameterUsername;
    fetch(url)
        .then((response) => {
            return response.json();
        })
        .then((messages) => {
            const messagesContainer = document.getElementById('message-container');
            if (messages.length == 0) {
                messagesContainer.innerHTML = '<p>This user has no posts yet.</p>';
            } else {
                messagesContainer.innerHTML = '';
            }
            messages.forEach((message) => {
                const messageDiv = buildMessageDiv(message);
                messagesContainer.appendChild(messageDiv);
            });
        });
}

/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildMessageDiv(message) {
    const headerDiv = document.createElement('div');
    headerDiv.classList.add('message-header');
    headerDiv.classList.add('padded');
    headerDiv.appendChild(document.createTextNode(
        message.user + ' - ' + new Date(message.timestamp)));

    const bodyDiv = document.createElement('div');
    bodyDiv.classList.add('message-body');
    bodyDiv.classList.add('padded');
    bodyDiv.innerHTML = message.text;

    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message-div');
    messageDiv.classList.add('rounded');
    messageDiv.classList.add('panel');
    messageDiv.appendChild(headerDiv);
    messageDiv.appendChild(bodyDiv);
    if (message.imageUrl) {
        bodyDiv.innerHTML += '<br/>';
        bodyDiv.innerHTML += '<img src="' + message.imageUrl + '" />';
    }

    return messageDiv;
}
=======
/** Build a language list to the app link
 **/
function buildLanguageLinks() {
    const userPageUrl = '/user-page.html?user=' + parameterUsername;
    const languagesListElement = document.getElementById('languages');
    languagesListElement.appendChild(createListItem(createLink(
        userPageUrl + '&language=en', 'English')));
    languagesListElement.appendChild(createListItem(createLink(
        userPageUrl + '&language=zh', 'Chinese')));
    languagesListElement.appendChild(createListItem(createLink(
        userPageUrl + '&language=hi', 'Hindi')));
    languagesListElement.appendChild(createListItem(createLink(
        userPageUrl + '&language=es', 'Spanish')));
    languagesListElement.appendChild(createListItem(createLink(
        userPageUrl + '&language=ar', 'Arabic')));
}


/** Fetches data and populates the UI of the page. */
function buildUI() {
    setPageTitle();
    showMessageFormIfLoggedIn();
    fetchMessages();
}