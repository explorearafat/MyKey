/* ============================================================
   MyKey — 3D Android phone
   ------------------------------------------------------------
   The screen is a canvas texture painted to look exactly like
   the app: a small chat mock on top and the REAL MyKey keyboard
   below — same row widths as the app's row_qwerty*.xml, the same
   dot-matrix key icons as its nth_*.xml drawables, the same
   theme colors as its Kotlin presets, and the app's own Dot
   Matrix key font (ndot, subset to ~40KB in assets).

   Redrawing for a theme change is just: repaint, needsUpdate.
   ============================================================ */
(function () {
  'use strict';

  var stage = document.getElementById('stage');
  if (!stage || typeof THREE === 'undefined') return;

  function boot() {

  /* ---------------- theme definitions ----------------
     Palettes lifted straight from the Kotlin theme presets;
     key/functional colors follow the app's contrast formula. */
  var THEMES = [
    { id:'warm-white',    name:'Warm White',    bg:'#FDF8F3', key:'#FFFFFF', func:'#F7EFE7', text:'#211A16', accent:'#B4552D', onAccent:'#FFFFFF' },
    { id:'classic-black', name:'Classic Black', bg:'#000000', key:'#0D0D0D', func:'#1C1C1F', text:'#E9E9EC', accent:'#E8E8EA', onAccent:'#121214' },
    { id:'midnight-blue', name:'Midnight Blue', bg:'#070D18', key:'#0D182C', func:'#1B2942', text:'#E2E8F4', accent:'#FF7A45', onAccent:'#0B1220' },
    { id:'forest',        name:'Forest',        bg:'#0B1410', key:'#14241D', func:'#22332A', text:'#DDE7DC', accent:'#7BD88F', onAccent:'#00391B' },
    { id:'sunset',        name:'Sunset',        bg:'#1A0E0A', key:'#2C1811', func:'#3B2419', text:'#F7E2D8', accent:'#FF8A5B', onAccent:'#4A1600' },
    { id:'monochrome',    name:'Monochrome',    bg:'#0A0A0A', key:'#171717', func:'#262626', text:'#EDEDED', accent:'#EDEDED', onAccent:'#1A1A1A' },
    { id:'rose-quartz',   name:'Rose Quartz',   bg:'#FFF8FA', key:'#FFFFFF', func:'#F9EDF2', text:'#1F1216', accent:'#C46A8E', onAccent:'#FFFFFF' },
    { id:'terminal',      name:'Terminal',      bg:'#040A06', key:'#0B1C11', func:'#15291C', text:'#C8FFE0', accent:'#3DFF9E', onAccent:'#00210F' }
  ];

  var currentTheme = THEMES[0];

  /* ---------------- dot-matrix key icons ----------------
     Every dot extracted from the app's res/drawable/nth_*.xml
     vector drawables (24x24 viewport). The keyboard's shift,
     backspace, enter and emoji keys really are painted from
     these exact dot positions. */
  var ICON_SHIFT   = [[18,15,4.133],[16,13,4.133],[14,11,4.133],[12,9,4.133],[10,11,4.133],[8,13,4.133],[6,15,4.133]];
  var ICON_DELETE  = [[21,4,1.944],[17.55,4,1.944],[14.101,4,1.944],[10.651,4,1.944],[7.474,4.601,1.944],[5.202,7.197,1.944],[2.931,9.793,1.944],[1.341,12.389,1.944],[3.612,14.986,1.944],[5.884,17.582,1.944],[8.236,20,1.944],[11.686,20,1.944],[15.136,20,1.944],[18.585,20,1.944],[21.988,19.736,1.944],[23,16.657,1.944],[23,13.207,1.944],[23,9.758,1.944],[23,6.308,1.944],[12,9,1.944],[13.5,10.5,1.944],[15,12,1.944],[16.5,13.5,1.944],[18,15,1.944],[18,9,1.944],[16.5,10.5,1.944],[13.5,13.5,1.944],[12,15,1.944]];
  var ICON_ENTER   = [[9,10,1],[6.5,12.5,1],[4,15,1],[6.5,17.5,1],[9,20,1],[20,4,1],[20,7.61,1],[19.99,11.22,1],[18.3,14.27,1],[14.84,15,1],[11.22,15,1],[7.61,15,1]];
  var ICON_SMILE   = [[9,9,1],[15,9,1],[12.005,2,1],[9.057,2.443,1],[6.373,3.737,1],[4.188,5.764,1],[2.698,8.344,1],[2.033,11.248,1],[2.256,14.219,1],[3.343,16.992,1],[5.199,19.322,1],[7.659,21.002,1],[10.505,21.882,1],[13.485,21.884,1],[16.332,21.007,1],[18.795,19.331,1],[20.656,17.003,1],[21.748,14.23,1],[21.963,11.258,1],[21.304,8.354,1],[19.817,5.773,1],[17.635,3.744,1],[14.952,2.448,1],[7.997,13.996,1],[9.778,15.44,1],[11.995,16,1],[14.213,15.445,1],[15.997,14.004,1]];

  /* ---------------- screen canvas ----------------
     900x1920 — the same 9:19.2 aspect as the phone's screen
     plane, so nothing is stretched. */
  var W = 900, H = 1920;
  var kbCanvas = document.createElement('canvas');
  kbCanvas.width = W;
  kbCanvas.height = H;
  var kx = kbCanvas.getContext('2d');

  function rgba(hex, alpha) {
    var h = hex.replace('#', '');
    var r = parseInt(h.substr(0, 2), 16),
        g = parseInt(h.substr(2, 2), 16),
        b = parseInt(h.substr(4, 2), 16);
    return 'rgba(' + r + ',' + g + ',' + b + ',' + alpha + ')';
  }

  function roundRect(ctx, x, y, w, h, r) {
    var rr = Math.min(r, w / 2, h / 2);
    ctx.beginPath();
    ctx.moveTo(x + rr, y);
    ctx.lineTo(x + w - rr, y);
    ctx.quadraticCurveTo(x + w, y, x + w, y + rr);
    ctx.lineTo(x + w, y + h - rr);
    ctx.quadraticCurveTo(x + w, y + h, x + w - rr, y + h);
    ctx.lineTo(x + rr, y + h);
    ctx.quadraticCurveTo(x, y + h, x, y + h - rr);
    ctx.lineTo(x, y + rr);
    ctx.quadraticCurveTo(x, y, x + rr, y);
    ctx.closePath();
  }

  function drawDots(dots, cx, cy, size, color) {
    var scale = size / 24;
    for (var i = 0; i < dots.length; i++) {
      var d = dots[i];
      kx.beginPath();
      kx.fillStyle = color;
      kx.arc(cx + (d[0] - 12) * scale, cy + (d[1] - 12) * scale, d[2] * scale, 0, Math.PI * 2, true);
      kx.fill();
    }
  }

  var CHAT = {
    chrome: '#FFFFFF', line: '#E8E3DC', bubbleIn: '#FFFFFF',
    bubbleOut: '#FFDBCB', bubbleOutText: '#3A1100',
    pageBg: '#F3F1EC', name: '#1F1B16', sub: '#7BA05B',
    inputBg: '#FFFFFF', inputLine: '#E4DED6', field: '#211A16',
    send: '#B4552D'
  };

  function drawScreen(t) {
    /* ---- page + status bar ---- */
    kx.fillStyle = CHAT.pageBg;
    kx.fillRect(0, 0, W, H);

    kx.fillStyle = CHAT.chrome;
    kx.fillRect(0, 0, W, 64);
    kx.fillStyle = CHAT.name;
    kx.font = '26px system-ui, sans-serif';
    kx.textAlign = 'left';
    kx.textBaseline = 'middle';
    kx.fillText('9:41', 34, 34);
    kx.textAlign = 'right';
    kx.fillRect(W - 44, 22, 22, 14);
    kx.beginPath();
    kx.arc(W - 62, 30, 6, 0, Math.PI * 2, true);
    kx.fill();

    /* ---- chat header ---- */
    kx.fillStyle = CHAT.chrome;
    kx.fillRect(0, 64, W, 118);
    kx.fillStyle = CHAT.line;
    kx.fillRect(0, 181, W, 2);

    kx.strokeStyle = CHAT.name;
    kx.lineWidth = 5;
    kx.lineCap = 'round';
    kx.beginPath();
    kx.moveTo(62, 98); kx.lineTo(46, 123); kx.lineTo(62, 148);
    kx.stroke();

    kx.beginPath();
    kx.fillStyle = '#EFE4D6';
    kx.arc(120, 123, 36, 0, Math.PI * 2, true);
    kx.fill();
    kx.beginPath();
    kx.fillStyle = '#52443A';
    kx.arc(120, 111, 14, 0, Math.PI * 2, true);
    kx.fill();
    kx.beginPath();
    kx.arc(120, 146, 22, Math.PI, Math.PI * 2, true);
    kx.fill();

    kx.textAlign = 'left';
    kx.fillStyle = CHAT.name;
    kx.font = '600 34px system-ui, sans-serif';
    kx.fillText('Arafat', 176, 112);
    kx.fillStyle = CHAT.sub;
    kx.font = '24px system-ui, sans-serif';
    kx.fillText('online', 176, 146);

    /* ---- bubbles ---- */
    var kbH = 700;
    var kbTop = H - kbH;
    var inputH = 118;
    var chatBottom = kbTop - inputH;

    function bubble(text, out, y) {
      kx.font = '30px system-ui, sans-serif';
      var tw = kx.measureText(text).width;
      var bw = tw + 56;
      var bh = 66;
      var bx = out ? W - 34 - bw : 34;
      kx.fillStyle = out ? CHAT.bubbleOut : CHAT.bubbleIn;
      roundRect(kx, bx, y, bw, bh, 22);
      kx.fill();
      kx.fillStyle = out ? CHAT.bubbleOutText : CHAT.name;
      kx.fillText(text, bx + 28, y + bh / 2 + 1);
      return y + bh + 26;
    }

    var y = 230;
    y = bubble('Did you try MyKey yet?', false, y);
    y = bubble('Typing in pixels right now', true, y);
    y = bubble('How does it feel?', false, y);

    /* ---- input row ---- */
    kx.fillStyle = CHAT.chrome;
    kx.fillRect(0, chatBottom, W, inputH);
    kx.fillStyle = CHAT.line;
    kx.fillRect(0, chatBottom, W, 2);

    var iy = chatBottom + inputH / 2;
    kx.fillStyle = CHAT.inputBg;
    roundRect(kx, 34, iy - 38, W - 68 - 110, 76, 38);
    kx.fill();
    kx.strokeStyle = CHAT.inputLine;
    kx.lineWidth = 2;
    kx.stroke();

    kx.fillStyle = CHAT.field;
    kx.font = '32px system-ui, sans-serif';
    kx.fillText('Hello', 70, iy + 1);
    kx.fillRect(70 + kx.measureText('Hello').width + 10, iy - 20, 3, 40);

    kx.fillStyle = CHAT.send;
    kx.beginPath();
    kx.arc(W - 92, iy, 38, 0, Math.PI * 2, true);
    kx.fill();
    kx.fillStyle = '#FFFFFF';
    kx.beginPath();
    kx.moveTo(W - 106, iy + 12);
    kx.lineTo(W - 74, iy);
    kx.lineTo(W - 106, iy - 12);
    kx.lineTo(W - 100, iy);
    kx.closePath();
    kx.fill();

    /* ---- keyboard ---- */
    drawKeyboard(t, kbTop, kbH);
  }

  /* the app's real row structure:
     row1 10x10%, row2 9x10% @5% indent, row3 shift 15% + 7x10% + delete 15%,
     row4 ?123 14% + emoji 10% + comma 9% + space 42% + period 10% + enter 15% */
  var ROWS = [
    { indent: 0,  weights: [10,10,10,10,10,10,10,10,10,10],
      keys: [{t:'l', c:'q'},{t:'l', c:'w'},{t:'l', c:'e'},{t:'l', c:'r'},{t:'l', c:'t'},{t:'l', c:'y'},{t:'l', c:'u'},{t:'l', c:'i'},{t:'l', c:'o'},{t:'l', c:'p'}] },
    { indent: 5,  weights: [10,10,10,10,10,10,10,10,10],
      keys: [{t:'l', c:'a'},{t:'l', c:'s'},{t:'l', c:'d'},{t:'l', c:'f'},{t:'l', c:'g'},{t:'l', c:'h'},{t:'l', c:'j'},{t:'l', c:'k'},{t:'l', c:'l'}] },
    { indent: 0,  weights: [15,10,10,10,10,10,10,10,10,15],
      keys: [{t:'shift'},{t:'l', c:'z'},{t:'l', c:'x'},{t:'l', c:'c'},{t:'l', c:'v'},{t:'l', c:'b'},{t:'l', c:'n'},{t:'l', c:'m'},{t:'delete'}] },
    { indent: 0,  weights: [14,10,9,42,10,15],
      keys: [{t:'symbol'},{t:'emoji'},{t:'comma'},{t:'space'},{t:'period'},{t:'enter'}] }
  ];
  var HINTS = '1234567890';
  var KEY_FONT = ' Ndot, ui-sans-serif, system-ui, sans-serif';

  function drawKeyboard(t, top, height) {
    kx.fillStyle = t.bg;
    kx.fillRect(0, top, W, height);

    var pad = 6, gap = 8;
    var unit = W / 100;
    var stripH = 96;

    /* suggestions — center is the typed word, sides are candidates */
    kx.textAlign = 'center';
    kx.textBaseline = 'middle';
    kx.font = '32px' + KEY_FONT;
    kx.fillStyle = rgba(t.text, 0.45);
    kx.fillText('the', W * 0.17, top + stripH / 2);
    kx.fillStyle = t.text;
    kx.fillText('hello', W * 0.50, top + stripH / 2 + 2);
    kx.fillStyle = rgba(t.text, 0.45);
    kx.fillText('mykey', W * 0.83, top + stripH / 2);

    var rowGap = 10;
    var rowH = (height - stripH - 20 - rowGap * 3) / 4;
    var radius = rowH * 0.16;
    var iconSize = rowH * 0.52;

    for (var r = 0; r < ROWS.length; r++) {
      var row = ROWS[r];
      var y = top + stripH + 12 + r * (rowH + rowGap);
      var x = row.indent * unit + pad;
      var hintIdx = 0;

      for (var i = 0; i < row.keys.length; i++) {
        var key = row.keys[i];
        var w = row.weights[i] * unit - gap;
        var mx = x + w / 2, my = y + rowH / 2;
        var isLetter = key.t === 'l';

        kx.fillStyle = (isLetter || key.t === 'space') ? t.key : t.func;
        roundRect(kx, x, y, w, rowH, radius);
        kx.fill();

        if (key.t === 'enter') {
          /* the app paints enter as a fully rounded primary pill */
          kx.fillStyle = t.accent;
          roundRect(kx, x, y, w, rowH, rowH / 2);
          kx.fill();
          drawDots(ICON_ENTER, mx, my, iconSize * 0.8, t.onAccent);
        } else if (key.t === 'shift') {
          drawDots(ICON_SHIFT, mx, my, iconSize, t.text);
        } else if (key.t === 'delete') {
          drawDots(ICON_DELETE, mx, my, iconSize, t.text);
        } else if (key.t === 'emoji') {
          drawDots(ICON_SMILE, mx, my, iconSize * 0.9, t.text);
        } else if (key.t === 'symbol') {
          kx.fillStyle = t.text;
          kx.font = '26px' + KEY_FONT;
          kx.fillText('?123', mx, my);
        } else if (key.t === 'space') {
          /* active language on the spacebar, the app's yellow */
          kx.fillStyle = rgba('#FFCC00', 0.85);
          kx.font = '30px' + KEY_FONT;
          kx.fillText('English', mx, my);
        } else if (key.t === 'comma') {
          kx.fillStyle = t.text;
          kx.font = '40px' + KEY_FONT;
          kx.fillText(',', mx, my + 6);
        } else if (key.t === 'period') {
          kx.fillStyle = t.text;
          kx.font = '40px' + KEY_FONT;
          kx.fillText('.', mx, my + 12);
        } else {
          kx.fillStyle = t.text;
          kx.font = '46px' + KEY_FONT;
          kx.fillText(key.c, mx, my + 2);
          if (r === 0) {
            kx.fillStyle = rgba(t.text, 0.4);
            kx.font = '20px' + KEY_FONT;
            kx.fillText(HINTS[hintIdx], x + w - gap * 1.6, y + rowH * 0.18);
          }
          hintIdx++;
        }

        x += row.weights[i] * unit;
      }
    }
  }

  drawScreen(currentTheme);

  var kbTexture = new THREE.CanvasTexture(kbCanvas);
  kbTexture.colorSpace = THREE.SRGBColorSpace;
  kbTexture.anisotropy = 8;
  kbTexture.minFilter = THREE.LinearFilter;
  kbTexture.magFilter = THREE.LinearFilter;
  kbTexture.generateMipmaps = false;

  /* ---------------- renderer ---------------- */
  var W3 = stage.clientWidth || 480;
  var H3 = stage.clientHeight || 480;

  var renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
  renderer.setPixelRatio(Math.min(window.devicePixelRatio || 1, 2));
  renderer.setSize(W3, H3, false);
  renderer.toneMapping = THREE.ACESFilmicToneMapping;
  renderer.toneMappingExposure = 1.15;
  stage.appendChild(renderer.domElement);

  var scene = new THREE.Scene();

  var camera = new THREE.PerspectiveCamera(34, W3 / H3, 0.1, 100);
  camera.position.set(0, 0, 7.6);

  /* ---------------- lighting ---------------- */
  scene.add(new THREE.AmbientLight(0xffffff, 0.55));

  var key = new THREE.DirectionalLight(0xffffff, 2.1);
  key.position.set(-4, 6, 6);
  scene.add(key);

  var rim = new THREE.DirectionalLight(0x4C8DF6, 1.5);
  rim.position.set(5, -2, -4);
  scene.add(rim);

  var fill = new THREE.PointLight(0xF2B233, 8, 18);
  fill.position.set(3, -4, 3);
  scene.add(fill);

  /* ---------------- phone geometry ---------------- */
  function roundedBoxShape(w, h, r) {
    var s = new THREE.Shape();
    var x = -w / 2, y = -h / 2;
    s.moveTo(x + r, y);
    s.lineTo(x + w - r, y);
    s.quadraticCurveTo(x + w, y, x + w, y + r);
    s.lineTo(x + w, y + h - r);
    s.quadraticCurveTo(x + w, y + h, x + w - r, y + h);
    s.lineTo(x + r, y + h);
    s.quadraticCurveTo(x, y + h, x, y + h - r);
    s.lineTo(x, y + r);
    s.quadraticCurveTo(x, y, x + r, y);
    return s;
  }

  var PHONE_W = 2.05;
  var PHONE_H = 4.15;
  var BEVEL = 0.10;

  var phoneGroup = new THREE.Group();
  scene.add(phoneGroup);

  var bodyGeo = new THREE.ExtrudeGeometry(
    roundedBoxShape(PHONE_W, PHONE_H, 0.30),
    { depth: 0.26, bevelEnabled: true, bevelThickness: BEVEL, bevelSize: BEVEL, bevelSegments: 6, curveSegments: 24 }
  );
  bodyGeo.center();

  var bodyMat = new THREE.MeshStandardMaterial({
    color: 0x22262e,
    metalness: 0.86,
    roughness: 0.31
  });
  var bodyMesh = new THREE.Mesh(bodyGeo, bodyMat);
  phoneGroup.add(bodyMesh);

  var screenW = PHONE_W - 0.20;
  var screenH = PHONE_H - 0.20;

  var screenGeo = new THREE.PlaneGeometry(screenW, screenH);
  var screenMat = new THREE.MeshBasicMaterial({ map: kbTexture });
  var screenMesh = new THREE.Mesh(screenGeo, screenMat);
  screenMesh.position.z = 0.26 / 2 + BEVEL + 0.001;
  phoneGroup.add(screenMesh);

  var glassGeo = new THREE.PlaneGeometry(screenW, screenH);
  var glassMat = new THREE.MeshPhysicalMaterial({
    color: 0xffffff,
    transparent: true,
    opacity: 0.06,
    roughness: 0.08,
    metalness: 0,
    clearcoat: 1,
    clearcoatRoughness: 0.06
  });
  var glassMesh = new THREE.Mesh(glassGeo, glassMat);
  glassMesh.position.z = screenMesh.position.z + 0.006;
  phoneGroup.add(glassMesh);

  var btnMat = new THREE.MeshStandardMaterial({ color: 0x2f343d, metalness: 0.9, roughness: 0.35 });
  [1.05, 0.75].forEach(function (yy) {
    var g = new THREE.BoxGeometry(0.045, 0.34, 0.13);
    var m = new THREE.Mesh(g, btnMat);
    m.position.set(PHONE_W / 2 + 0.035, yy, 0);
    phoneGroup.add(m);
  });

  phoneGroup.rotation.y = -0.30;
  phoneGroup.rotation.x = 0.10;

  /* ---------------- interaction ---------------- */
  var dragging = false;
  var lastX = 0, lastY = 0;
  var targetRotY = phoneGroup.rotation.y;
  var targetRotX = phoneGroup.rotation.x;
  var autoSpin = true;

  function onDown(e) {
    dragging = true;
    autoSpin = false;
    lastX = e.clientX;
    lastY = e.clientY;
    stage.style.cursor = 'grabbing';
  }
  function onMove(e) {
    if (!dragging) return;
    var dx = (e.clientX - lastX) / 220;
    var dy = (e.clientY - lastY) / 260;
    targetRotY = Math.max(-0.85, Math.min(0.85, targetRotY + dx));
    targetRotX = Math.max(-0.45, Math.min(0.45, targetRotX + dy));
    lastX = e.clientX;
    lastY = e.clientY;
  }
  function onUp() {
    dragging = false;
    stage.style.cursor = 'grab';
  }

  stage.style.cursor = 'grab';
  stage.addEventListener('pointerdown', onDown);
  window.addEventListener('pointermove', onMove);
  window.addEventListener('pointerup', onUp);
  window.addEventListener('pointercancel', onUp);

  /* ---------------- resize ---------------- */
  function resize() {
    var w = stage.clientWidth;
    var h = stage.clientHeight;
    if (!w || !h) return;
    renderer.setSize(w, h, false);
    camera.aspect = w / h;
    camera.updateProjectionMatrix();
  }
  window.addEventListener('resize', resize);
  if (window.ResizeObserver) new ResizeObserver(resize).observe(stage);

  /* ---------------- loop ---------------- */
  var clock = new THREE.Clock();

  function tick() {
    var t = clock.getElapsedTime();

    if (autoSpin) {
      targetRotY = Math.sin(t * 0.34) * 0.24;
      targetRotX = 0.10 + Math.sin(t * 0.23) * 0.05;
    }

    phoneGroup.rotation.y += (targetRotY - phoneGroup.rotation.y) * 0.09;
    phoneGroup.rotation.x += (targetRotX - phoneGroup.rotation.x) * 0.09;

    phoneGroup.position.y = Math.sin(t * 0.85) * 0.055;

    renderer.render(scene, camera);
    requestAnimationFrame(tick);
  }
  requestAnimationFrame(tick);

  /* ---------------- public API for the theme switcher ---------------- */
  function setTheme(themeId) {
    var found = null;
    for (var i = 0; i < THEMES.length; i++) {
      if (THEMES[i].id === themeId) { found = THEMES[i]; break; }
    }
    if (!found) return;
    currentTheme = found;
    drawScreen(found);
    kbTexture.needsUpdate = true;
  }

  window.MyKeyScene = {
    themes: THEMES,
    setTheme: setTheme
  };

  window.dispatchEvent(new CustomEvent('mykey:scene-ready'));
  }

  /* boot after the app's key font is available so key labels render
     in Dot Matrix like the real keyboard; falls back to sans. */
  var KEYFONT_SRC = "url('assets/ndot-subset.ttf')";
  if (document.fonts && window.FontFace) {
    var kf = new FontFace('Ndot', KEYFONT_SRC);
    kf.load().then(
      function (f) { document.fonts.add(f); boot(); },
      function () { boot(); }
    );
  } else {
    boot();
  }
})();
