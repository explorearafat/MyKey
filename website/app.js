/* ============================================================
   MyKey — page behaviour
   Kept deliberately tiny: theme swatches for the 3D phone,
   scroll reveal, current year. No framework, no dependencies.
   ============================================================ */
(function () {
  'use strict';

  /* ---------- footer year ---------- */
  var yearEl = document.getElementById('year');
  if (yearEl) yearEl.textContent = new Date().getFullYear();

  /* ---------- theme switcher ----------
     The swatches are generated from the scene's own theme list, so
     adding a theme in three-scene.js automatically adds a button here.
     No second list to keep in sync. */
  var switcher = document.getElementById('switcher');

  function buildSwitcher(themes) {
    if (!switcher) return;
    switcher.innerHTML = '';

    themes.forEach(function (theme, index) {
      var btn = document.createElement('button');
      btn.type = 'button';
      btn.className = 'swatch' + (index === 0 ? ' is-active' : '');
      btn.setAttribute('role', 'tab');
      btn.setAttribute('aria-selected', index === 0 ? 'true' : 'false');
      btn.dataset.theme = theme.id;

      var dot = document.createElement('i');
      dot.style.background = theme.accent;

      btn.appendChild(dot);
      btn.appendChild(document.createTextNode(theme.name));

      btn.addEventListener('click', function () {
        /* clear previous active state */
        var prev = switcher.querySelector('.swatch.is-active');
        if (prev) {
          prev.classList.remove('is-active');
          prev.setAttribute('aria-selected', 'false');
        }
        btn.classList.add('is-active');
        btn.setAttribute('aria-selected', 'true');

        if (window.MyKeyScene) window.MyKeyScene.setTheme(theme.id);
      });

      switcher.appendChild(btn);
    });
  }

  /* the scene may not be ready yet on slow connections */
  if (window.MyKeyScene) {
    buildSwitcher(window.MyKeyScene.themes);
  } else {
    window.addEventListener('mykey:scene-ready', function () {
      buildSwitcher(window.MyKeyScene.themes);
    });
  }

  /* ---------- scroll reveal ----------
     IntersectionObserver rather than a scroll handler: no layout
     thrash, and it costs nothing when elements are off-screen. */
  var reveals = document.querySelectorAll('.reveal');

  if ('IntersectionObserver' in window) {
    var io = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          entry.target.classList.add('in');
          io.unobserve(entry.target);
        }
      });
    }, { rootMargin: '0px 0px -12% 0px', threshold: 0.08 });

    reveals.forEach(function (el) { io.observe(el); });
  } else {
    /* older browsers: just show everything */
    reveals.forEach(function (el) { el.classList.add('in'); });
  }

  /* ---------- smooth anchor offset ----------
     The sticky nav is 66px tall, so anchor jumps need to land below it. */
  document.querySelectorAll('a[href^="#"]').forEach(function (link) {
    link.addEventListener('click', function (e) {
      var id = link.getAttribute('href');
      if (!id || id === '#') return;
      var target = document.querySelector(id);
      if (!target) return;
      e.preventDefault();
      var y = target.getBoundingClientRect().top + window.pageYOffset - 76;
      window.scrollTo({ top: y, behavior: 'smooth' });
    });
  });
})();
