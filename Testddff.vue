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
      viewportWidth: 0
    };
  },
  computed: {
    slidesStyle() {
      const translate = -this.currentIndex * 100;
      const dragPx = this.dragging ? this.dragDeltaX : 0;

      return {
        transform: `translate3d(calc(${translate}% + ${dragPx}px), 0, 0)`,
        transition: this.dragging ? "none" : "transform 300ms ease"
      };
    }
  },
  mounted() {
    this.updateSlidesCount();
    this.updateViewportWidth();
    window.addEventListener("resize", this.updateViewportWidth);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.updateViewportWidth);
  },
  methods: {
    updateSlidesCount() {
      const slot = this.$slots.default ? this.$slots.default() : [];
      this.slidesCount = slot.length || 0;
    },
    updateViewportWidth() {
      this.viewportWidth = this.$refs.viewport?.clientWidth || 0;
    },

    // ------- 드래그 / 터치 -------
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

      const threshold = Math.min(80, this.viewportWidth * 0.15);

      if (this.dragDeltaX > threshold && this.currentIndex > 0) {
        this.currentIndex--;
      } else if (this.dragDeltaX < -threshold && this.currentIndex < this.slidesCount - 1) {
        this.currentIndex++;
      }

      this.dragging = false;
      this.dragDeltaX = 0;
    },

    // 터치
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
  position: relative;
  width: 100%;
  overflow: hidden;
}

.slides {
  display: flex;
  width: 100%;
  will-change: transform;
}

/* 슬롯으로 들어온 자식들을 100% 폭으로 설정 */
.slides ::v-deep > * {
  flex: 0 0 100%;
  width: 100%;
}
</style>
