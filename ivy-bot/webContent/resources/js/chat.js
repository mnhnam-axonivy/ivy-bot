/**
 * Chat UI JavaScript
 *
 * Called from ChatBot.xhtml via p:ajax onstart/oncomplete callbacks.
 *
 * Flow:
 *   1. User clicks Send (or presses Enter)
 *   2. onstart  -> showThinking()   : show user bubble instantly + thinking indicator
 *   3. Server processes the message (AJAX)
 *   4. oncomplete -> onChatComplete(): replace temp DOM with server-rendered messages
 */

// ---------------------------------------------------------------------------
// DOM helpers
// ---------------------------------------------------------------------------
function getChatInput() {
  return document.querySelector('[id$="chatInput"]');
}

function getSendBtn() {
  return document.querySelector('[id$="sendBtn"]');
}

function scrollToBottom() {
  var area = document.querySelector('.messages-area');
  if (area) {
    setTimeout(function () { area.scrollTop = area.scrollHeight; }, 100);
  }
}

function escapeHtml(text) {
  var div = document.createElement('div');
  div.textContent = text;
  return div.innerHTML;
}

function setInputEnabled(enabled) {
  var input = getChatInput();
  var btn   = getSendBtn();
  if (input) { input.disabled = !enabled; }
  if (btn)   { btn.disabled   = !enabled; }
}

// ---------------------------------------------------------------------------
// Thinking indicator
// ---------------------------------------------------------------------------

function setThinkingVisible(visible) {
  var el = document.getElementById('thinkingIndicator');
  if (el) { el.style.display = visible ? 'block' : 'none'; }
}

// ---------------------------------------------------------------------------
// Temporary user message (shown instantly before AJAX completes)
// ---------------------------------------------------------------------------
function appendUserMessage(content) {
  var chatContent = document.querySelector('.chat-content');
  if (!chatContent) { return; }

  // Hide welcome screen
  var welcome = chatContent.querySelector('.flex.flex-column.align-items-center');
  if (welcome) { welcome.remove(); }

  var time = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

  var row = document.createElement('div');
  row.className = 'flex justify-content-end mb-3 temp-user-message';
  row.innerHTML =
    '<div style="max-width: 75%;">' +
      '<div class="p-3 border-round-xl text-white" style="background: var(--primary-color);">' +
        escapeHtml(content) +
      '</div>' +
      '<div class="text-xs text-color-secondary mt-1 text-right">' + time + '</div>' +
    '</div>';

  var indicator = document.getElementById('thinkingIndicator');
  if (indicator) {
    chatContent.insertBefore(row, indicator);
  } else {
    chatContent.appendChild(row);
  }

  scrollToBottom();
}

function removeTempMessages() {
  document.querySelectorAll('.temp-user-message').forEach(function (el) {
    el.remove();
  });
}

// ---------------------------------------------------------------------------
// AJAX callbacks (referenced in ChatBot.xhtml)
// ---------------------------------------------------------------------------

/** Called by p:ajax onstart — runs BEFORE the request is sent. */
function showThinking() {
  var input = getChatInput();

  // Instantly show the user's message in the DOM
  if (input && input.value.trim()) {
    appendUserMessage(input.value.trim());
  }

  // Clear & disable input after PrimeFaces has captured the value
  setTimeout(function () {
    if (input) { input.value = ''; }
    setInputEnabled(false);
  }, 50);

  setThinkingVisible(true);
  scrollToBottom();
}

/** Called by p:ajax oncomplete — runs AFTER the server response is applied. */
function onChatComplete() {
  setThinkingVisible(false);
  removeTempMessages();
  setInputEnabled(true);

  var input = getChatInput();
  if (input) {
    setTimeout(function () { input.focus(); }, 100);
  }

  setTimeout(scrollToBottom, 150);
}

// ---------------------------------------------------------------------------
// Keyboard handling
// ---------------------------------------------------------------------------

/** Enter sends; Shift+Enter inserts a newline. */
function handleChatInputKeydown(event) {
  if (event.key === 'Enter' && !event.shiftKey) {
    event.preventDefault();
    var input = getChatInput();
    if (input && input.disabled) { return; }
    var btn = getSendBtn();
    if (btn && !btn.disabled) { btn.click(); }
  }
}

// ---------------------------------------------------------------------------
// Init
// ---------------------------------------------------------------------------
$(document).ready(function () {
  scrollToBottom();
  var input = getChatInput();
  if (input) { input.focus(); }
});
