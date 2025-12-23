<script>
export default {
  name: "App",

  mounted() {
    this.loadAnalytics();
  },

  methods: {
    loadAnalytics() {
      // ---------- Google Tag Manager ----------
      if (!window.dataLayer) {
        window.dataLayer = [];
      }

      window.dataLayer.push({
        "gtm.start": new Date().getTime(),
        event: "gtm.js"
      });

      const gtm = document.createElement("script");
      gtm.src = "https://www.googletagmanager.com/gtm.js?id=GTM-WXMK2VFK";
      gtm.async = true;
      document.head.appendChild(gtm);

      // ---------- Google Analytics ----------
      const ga = document.createElement("script");
      ga.src = "https://www.googletagmanager.com/gtag/js?id=G-RG1EE8488N";
      ga.async = true;
      document.head.appendChild(ga);

      window.gtag = function () {
        window.dataLayer.push(arguments);
      };

      window.gtag("js", new Date());
      window.gtag("config", "G-RG1EE8488N");

      // ---------- Naver Analytics ----------
      const na = document.createElement("script");
      na.src = "https://wcs.naver.net/wcslog.js";
      na.async = true;

      na.onload = () => {
        if (!window.wcs_add) {
          window.wcs_add = {};
        }
        window.wcs_add["wa"] = "104edc0db2a1900";
        if (window.wcs) {
          window.wcs_do();
        }
      };

      document.body.appendChild(na);
    }
  }
};
</script>
