/**
 * Talent Acquisition — Welcome page 3-slide carousel.
 * Reuses fd- CSS classes (bundled in talent-welcome.css) for panel
 * visibility and animation. Button IDs are pw-prev / pw-next.
 */
(function () {
  'use strict';

  var TOTAL_SLIDES = 3;
  var currentSlide = 0;
  var wrapper = null;

  /* ── Slide navigation ──────────────────────────────────────────── */
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

  /* ── Public API ────────────────────────────────────────────────── */
  window.pwGoToSlide = function (index) {
    goToSlide(index, index < currentSlide);
  };

  window.pwPrevSlide = function () {
    if (currentSlide > 0) goToSlide(currentSlide - 1, true);
  };

  window.pwNextSlide = function () {
    if (currentSlide < TOTAL_SLIDES - 1) goToSlide(currentSlide + 1, false);
  };

  /* ── Bootstrap ─────────────────────────────────────────────────── */
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
