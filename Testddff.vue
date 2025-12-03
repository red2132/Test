<template>
  <div
    class="carousel"
    ref="viewport"
    @mousedown.prevent="onDragStart"
    @mousemove.prevent="onDragMove"
    @mouseup.prevent="onDragEnd"
    @mouseleave.prevent="onDragEnd"
    @touchstart.prevent="onTouchStart"
    @touchmove.prevent="onTouchMove"
    @touchend.prevent="onDragEnd"
  >
    <div
      class="slides"
      :style="slidesStyle"
      ref="slides"
    >
      <slot />
    </div>
  </div>
</template>

<script>
export default {
  name: "Carousel",
  data() {
    return {
      currentIndex: 0,
      slidesCount: 0,
      dragging: false,
      dragStartX: 0,
      dragDeltaX: 0,
      slideWidths: [],
      totalWidth: 0
    };
  },
  computed: {
    slidesStyle() {
      // 현재 index까지의 width 합으로 translate 계산
      const baseOffset = this.slideWidths
        .slice(0, this.currentIndex)
        .reduce((a, b) => a + b, 0);

      const dragOffset = this.dragDeltaX;

      return {
        transform: `translate3d(${-(baseOffset - dragOffset)}px, 0, 0)`,
        transition: this.dragging ? "none" : "transform 300ms ease"
      };
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.measureSlides();
    });
    window.addEventListener("resize", this.measureSlides);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.measureSlides);
  },
  methods: {
    measureSlides() {
      const slides = this.$refs.slides?.children || [];
      this.slideWidths = Array.from(slides).map(el => el.clientWidth);
      this.slidesCount = this.slideWidths.length;
      this.totalWidth = this.slideWidths.reduce((a, b) => a + b, 0);
    },

    // ------- DRAG -------
    onDragStart(e) {
      this.dragging = true;
      this.dragStartX = e.clientX;
      this.dragDeltaX = 0;
    },
    onDragMove(e) {
      if (!this.dragging) return;
      this.dragDeltaX = e.clientX - this.dragStartX;
    },
    onDragEnd() {
      if (!this.dragging) return;

      const threshold = 60;

      if (this.dragDeltaX > threshold && this.currentIndex > 0) {
        this.currentIndex--;
      } else if (this.dragDeltaX < -threshold && this.currentIndex < this.slidesCount - 1) {
        this.currentIndex++;
      }

      this.dragging = false;
      this.dragDeltaX = 0;
    },

    // ------- TOUCH -------
    onTouchStart(e) {
      const t = e.touches[0];
      this.dragging = true;
      this.dragStartX = t.clientX;
      this.dragDeltaX = 0;
    },
    onTouchMove(e) {
      if (!this.dragging) return;
      const t = e.touches[0];
      this.dragDeltaX = t.clientX - this.dragStartX;
    }
  }
};
</script>

<style scoped>
.carousel {
  overflow: hidden;
  width: 100%;
}

.slides {
  display: flex;
  will-change: transform;
}

/* slot 요소는 크기 강제하지 않음 */
</style>
