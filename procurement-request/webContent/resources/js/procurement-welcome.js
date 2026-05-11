/**
 * Procurement Request — Welcome page 3-slide carousel.
 * Reuses fd- CSS classes from finish-demo.css for panel visibility and
 * animation (fd-step-panel, fd-active, fd-go-back, fd-dot, fd-dot-active).
 * Button IDs are pw-prev / pw-next to avoid collisions.
 */
(function () {
  'use strict';

  var TOTAL_SLIDES = 3;
  var currentSlide = 0;
  var wrapper = null;

  /* ── Slide navigation ──────────────────────────────────────── */
  function goToSlide(index, isBack) {
    if (index < 0 || index >= TOTAL_SLIDES) return;

    var prev = wrapper.querySelector('.fd-step-panel.fd-active');
    if (prev) prev.classList.remove('fd-active');

    wrapper.classList.toggle('fd-go-back', isBack);

    var next = wrapper.querySelector('.fd-step-panel[data-step="' + index + '"]');
    if (next) next.classList.add('fd-active');

    currentSlide = index;
    syncUI();
  }

  function syncUI() {
    var i = currentSlide;

    document.querySelectorAll('.fd-dot').forEach(function (dot, idx) {
      dot.classList.toggle('fd-dot-active', idx === i);
    });

    var btnPrev = document.getElementById('pw-prev');
    var btnNext = document.getElementById('pw-next');
    if (btnPrev) btnPrev.disabled = (i === 0);
    if (btnNext) btnNext.disabled = (i === TOTAL_SLIDES - 1);
  }

  /* ── Public API ─────────────────────────────────────────────── */
  window.pwGoToSlide = function (index) {
    goToSlide(index, index < currentSlide);
  };

  window.pwPrevSlide = function () {
    if (currentSlide > 0) goToSlide(currentSlide - 1, true);
  };

  window.pwNextSlide = function () {
    if (currentSlide < TOTAL_SLIDES - 1) goToSlide(currentSlide + 1, false);
  };

  /* ── Bootstrap ─────────────────────────────────────────────── */
  function init() {
    wrapper = document.querySelector('.pw-page .fd-steps-wrapper');
    if (!wrapper) return;

    var first = wrapper.querySelector('.fd-step-panel[data-step="0"]');
    if (first) first.classList.add('fd-active');

    syncUI();
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    init();
  }

})();

/**
 * pwGen — sequential data generation for slide 2 (Ready to start?).
 * Calls p:remoteCommand functions pwGenStep1 one at a time, then enables Proceed.
 */
var pwGen = (function () {
  'use strict';

  var _running = false;
  var TOTAL_STEPS = 1;

  var STEP_SUBTITLES = [
    'Generating material types, items and inventory…'
  ];

  function _getGenBtn() {
    return document.getElementById('pw-gen-form:pwGenBtn');
  }

  function _setStepRunning(n) {
    var tl = document.querySelector('.js-pw-tl');
    if (!tl) { return; }
    var items = tl.querySelectorAll('.so-checklist-item');
    var item  = items[n - 1];
    if (!item) { return; }

    item.className = 'so-checklist-item running so-tl-item';

    var bubble = item.querySelector('.so-tl-bubble');
    if (bubble) { bubble.className = 'so-tl-bubble so-tl-bubble-running'; }

    var icon = item.querySelector('.so-tl-bubble i');
    if (icon) { icon.className = 'ti ti-loader so-spin'; }

    var card = item.querySelector('.so-tl-card');
    var sub  = item.querySelector('.so-checklist-subtitle');
    if (!sub && card) {
      sub = document.createElement('div');
      sub.className = 'so-checklist-subtitle';
      card.appendChild(sub);
    }
    if (sub) { sub.textContent = STEP_SUBTITLES[n - 1] || 'Processing…'; }
  }

  function _invokeStep(n) {
    _setStepRunning(n);
    var fn = window['pwGenStep' + n];
    if (typeof fn === 'function') { fn(); }
  }

  function start() {
    if (_running) { return; }
    _running = true;

    var tl = document.querySelector('.js-pw-gen-timeline');
    if (tl) { tl.classList.remove('hidden'); tl.style.display = ''; }

    var btn = _getGenBtn();
    if (btn) {
      btn.disabled = true;
      btn.classList.add('ui-state-disabled');
    }

    _invokeStep(1);
  }

  function onStepDone(n) {
    if (n < TOTAL_STEPS) {
      _invokeStep(n + 1);
    } else {
      _onAllDone();
    }
  }

  function onStepError(n) {
    _running = false;
    var btn = _getGenBtn();
    if (btn) {
      btn.disabled = false;
      btn.classList.remove('ui-state-disabled');
      var text = btn.querySelector('.ui-button-text');
      if (text) { text.textContent = 'Retry'; }
    }
  }

  function _onAllDone() {
    _running = false;

    var btn = _getGenBtn();
    if (btn) {
      btn.disabled = true;
      btn.classList.add('ui-state-disabled');
    }

    // Enable the Proceed button
    var proceed = document.getElementById('pw-footer-form:proceed');
    if (proceed) {
      proceed.disabled = false;
      proceed.classList.remove('ui-state-disabled');
    }
  }

  return { start: start, onStepDone: onStepDone, onStepError: onStepError };

}());
