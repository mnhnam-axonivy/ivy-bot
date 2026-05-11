/**
 * AI Assistant — messenger-style lifecycle for the AiAssistant composite component.
 */

var _assistantThinkingInterval = null;
var _assistantThinkingIndex = 0;

function _assistantGetThinkingPhrases(clientId) {
  var phrases = [];
  var i = 0;
  var el;
  while ((el = document.getElementById(clientId + '-i18n-phrase-' + i)) !== null) {
    var text = el.textContent.trim();
    if (text) { phrases.push(text); }
    i++;
  }
  if (phrases.length > 0) { return phrases; }
  var fallbackEl = document.getElementById(clientId + '-i18n-thinking-fallback');
  return [fallbackEl ? fallbackEl.textContent.trim() : 'Thinking...'];
}

// ── Private helpers ──────────────────────────────────────────────────────────

function _assistantScrollToBottom(clientId) {
  var area = document.getElementById(clientId + '-messages-area');
  if (!area) { return; }
  setTimeout(function() {
    area.scrollTop = area.scrollHeight;
  }, 100);
}

function _assistantGetWrapper(clientId) {
  return document.getElementById(clientId);
}

function _assistantSetInputState(clientId, disabled) {
  var wrapper = _assistantGetWrapper(clientId);
  var input   = wrapper ? wrapper.querySelector('.message-input') : null;
  var sendBtn = wrapper ? wrapper.querySelector('[id$="assistant-send"]') : null;
  var chips   = wrapper ? wrapper.querySelectorAll('.so-ai-chip') : [];
  if (input) {
    input.disabled = disabled;
    if (disabled) { input.classList.add('disabled-input'); }
    else          { input.classList.remove('disabled-input'); }
  }
  if (sendBtn) {
    sendBtn.disabled = disabled;
    if (disabled) { sendBtn.classList.add('disabled-btn'); }
    else          { sendBtn.classList.remove('disabled-btn'); }
  }
  chips.forEach(function(chip) {
    if (disabled) { chip.classList.add('disabled-chip'); }
    else          { chip.classList.remove('disabled-chip'); }
  });
}

/**
 * Adds the fade-in animation class to the most recently added user message row.
 */
function _assistantAnimateLastUserMessage(clientId) {
  var wrapper = _assistantGetWrapper(clientId);
  if (!wrapper) { return; }
  var rows = wrapper.querySelectorAll('.user-message-row:not(.so-ai-message-enter)');
  if (rows.length === 0) { return; }
  rows[rows.length - 1].classList.add('so-ai-message-enter');
}

/**
 * Typewriter-streams the content of the most recently added assistant message.
 */
function _assistantRevealFeedbackCard(card) {
  card.classList.remove('so-ai-feedback-hidden');
  card.classList.add('so-ai-feedback-visible');
}

function _assistantStreamLastMessage(clientId) {
  var wrapper = _assistantGetWrapper(clientId);
  if (!wrapper) { return; }
  var rows = wrapper.querySelectorAll('.assistant-message-row:not(.thinking-status):not(.so-ai-streamed)');
  if (rows.length === 0) { return; }
  var lastRow = rows[rows.length - 1];
  lastRow.classList.add('so-ai-streamed', 'so-ai-assistant-enter');

  var feedbackCard = lastRow.querySelector('.so-ai-feedback-card');
  if (feedbackCard) { feedbackCard.classList.add('so-ai-feedback-hidden'); }

  var contentEl = lastRow.querySelector('.message-content');
  if (!contentEl) {
    if (feedbackCard) { setTimeout(function() { _assistantRevealFeedbackCard(feedbackCard); }, 300); }
    return;
  }

  var fullText = contentEl.textContent || '';
  contentEl.textContent = '';

  var cursor = document.createElement('span');
  cursor.className = 'so-ai-streaming-cursor';
  contentEl.appendChild(cursor);

  var delay = 15;
  var i = 0;

  function typeNext() {
    if (i < fullText.length) {
      cursor.insertAdjacentText('beforebegin', fullText[i]);
      i++;
      setTimeout(typeNext, delay);
    } else {
      cursor.remove();
      if (feedbackCard) {
        setTimeout(function() { _assistantRevealFeedbackCard(feedbackCard); }, 350);
      }
    }
  }
  typeNext();
}

// ── Public API ───────────────────────────────────────────────────────────────

/**
 * Shows the thinking indicator and starts phrase cycling.
 * Called from oncomplete of the send button.
 * @param {string} clientId  composite component clientId
 */
function assistantShowThinking(clientId) {
  var thinking = document.getElementById(clientId + '-thinking');
  if (thinking) { thinking.classList.remove('rag-hidden'); }

  var phrases = _assistantGetThinkingPhrases(clientId);
  _assistantThinkingIndex = 0;
  var thinkingText = document.getElementById(clientId + '-thinking-text');
  if (thinkingText) {
    thinkingText.textContent = phrases[0] + '...';
    if (_assistantThinkingInterval) { clearInterval(_assistantThinkingInterval); }
    _assistantThinkingInterval = setInterval(function() {
      _assistantThinkingIndex = (_assistantThinkingIndex + 1) % phrases.length;
      thinkingText.textContent = phrases[_assistantThinkingIndex] + '...';
    }, 2000);
  }

  _assistantSetInputState(clientId, true);
  _assistantAnimateLastUserMessage(clientId);
  _assistantScrollToBottom(clientId);
}

/**
 * Clears the thinking interval, re-enables input, and scrolls to bottom.
 * Called from oncomplete of the assistantGetAnswer remoteCommand.
 * @param {string} clientId  composite component clientId
 */
function assistantAfterAnswer(clientId) {
  if (_assistantThinkingInterval) {
    clearInterval(_assistantThinkingInterval);
    _assistantThinkingInterval = null;
  }
  _assistantSetInputState(clientId, false);
  _assistantStreamLastMessage(clientId);
  var wrapper = _assistantGetWrapper(clientId);
  var input = wrapper ? wrapper.querySelector('.message-input') : null;
  if (input) { setTimeout(function() { input.focus(); }, 100); }
  _assistantScrollToBottom(clientId);
}

/**
 * Submits the chat input on Enter key (without Shift).
 * @param {KeyboardEvent} event
 */
function assistantHandleKeyDown(event) {
  if (event.key === 'Enter' && !event.shiftKey) {
    event.preventDefault();
    if (!event.target.value || !event.target.value.trim()) { return; }
    PF('assistantSendCmd').jq.click();
  }
}

/**
 * Fills the hidden chip-question input with the chip's question and fires
 * the dedicated assistantChipSend remote command directly.
 * @param {HTMLElement} chip
 */
function assistantChipClick(chip) {
  var question = chip.getAttribute('data-question').trim();
  var wrapper  = chip.closest('.so-ai-chat-wrapper');
  var hidden   = wrapper ? wrapper.querySelector('[id$="assistant-chip-question"]') : null;
  if (hidden) {
    hidden.value = question;
  }
  assistantChipSend();
}
