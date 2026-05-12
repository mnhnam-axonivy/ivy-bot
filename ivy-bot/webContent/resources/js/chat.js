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
// Markdown rendering
// ---------------------------------------------------------------------------

function renderMarkdown() {
  if (typeof marked === 'undefined') { return; }
  document.querySelectorAll('.ai-markdown-content').forEach(function (el) {
    if (el.dataset.rendered) { return; }
    var raw = el.textContent;
    el.innerHTML = marked.parse(raw);
    el.dataset.rendered = 'true';
  });
}

// ---------------------------------------------------------------------------
// Translation toggle
// ---------------------------------------------------------------------------

/**
 * Toggles between original and translated text for any message bubble.
 *
 * For AI messages (data-showing-original="false"):
 *   default view = translated (user's language) → toggle shows original (English)
 * For USER messages (data-showing-original="true"):
 *   default view = original (user's language) → toggle shows translated (English)
 */
function toggleOriginal(link) {
  var wrapper = link.closest('.assistant-content') || link.closest('.user-bubble');
  if (!wrapper) { return; }
  var contentDiv = wrapper.querySelector('[data-original]');
  if (!contentDiv) { return; }

  var isShowingOriginal = link.dataset.showingOriginal === 'true';
  var labelShowOriginal = link.dataset.labelShowOriginal || 'Show original';
  var labelShowTranslation = link.dataset.labelShowTranslation || 'Show translation';

  if (isShowingOriginal) {
    // Switch to translated view
    var translated = contentDiv.dataset.translated;
    contentDiv.innerHTML = typeof marked !== 'undefined' ? marked.parse(translated) : translated;
    link.textContent = labelShowOriginal;
    link.dataset.showingOriginal = 'false';
  } else {
    // Switch to original view
    var original = contentDiv.dataset.original;
    contentDiv.innerHTML = typeof marked !== 'undefined' ? marked.parse(original) : original;
    link.textContent = labelShowTranslation;
    link.dataset.showingOriginal = 'true';
  }
}

function initTranslationToggles() {
  // Labels are initialized via HTML data attributes — nothing to do here
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
  renderMarkdown();
  initTranslationToggles();
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
  renderMarkdown();
  initTranslationToggles();
  scrollToBottom();
  var input = getChatInput();
  if (input) { input.focus(); }
});
