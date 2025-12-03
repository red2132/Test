<template>
  <div class="draggable-slider">
    <button v-if="showArrows" class="arrow left" @click="scrollBy(-viewportWidth / 2)" :disabled="isAtStart">◀</button>

    <div
      class="viewport"
      ref="viewport"
      @mousedown="onDown"
      @touchstart="onDown"
      @mousemove="onMove"
      @touchmove.prevent="onMove"
      @mouseup="onUp"
      @mouseleave="onLeave"
      @touchend="onUp"
      @wheel.passive="onWheel"
    >
      <div class="track" ref="track">
        <!-- Users pass child items as slot content. Each child should have .slide-item (or style will work too) -->
        <slot>
          <!-- 예시 항목 (실제 사용시 slot으로 대체) -->
          <div class="slide-item" v-for="n in 8" :key="n">Item {{ n }}</div>
        </slot>
      </div>
    </div>

    <button v-if="showArrows" class="arrow right" @click="scrollBy(viewportWidth / 2)" :disabled="isAtEnd">▶</button>
  </div>
</template>

<script>
export default {
  name: "DraggableSlider",
  props: {
    gap: { type: Number, default: 12 },
    showArrows: { type: Boolean, default: true }
  },
  data() {
    return {
      isDown: false,
      startX: 0,
      scrollLeftStart: 0,
      viewportWidth: 0,
      isAtStart: true,
      isAtEnd: false
    };
  },
  mounted() {
    this.updateMeasurements();
    window.addEventListener("resize", this.updateMeasurements);
    // prevent image drag ghosting inside slider
    this.$refs.viewport.addEventListener("dragstart", (e) => e.preventDefault());
    this.checkEdge();
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.updateMeasurements);
  },
  methods: {
    updateMeasurements() {
      this.viewportWidth = this.$refs.viewport.clientWidth;
      this.checkEdge();
    },
    onDown(e) {
      // support touch and mouse
      this.isDown = true;
      this.$refs.viewport.classList.add("dragging");
      this.startX = (e.touches ? e.touches[0].pageX : e.pageX) - this.$refs.viewport.offsetLeft;
      this.scrollLeftStart = this.$refs.viewport.scrollLeft;
    },
    onMove(e) {
      if (!this.isDown) return;
      const x = (e.touches ? e.touches[0].pageX : e.pageX) - this.$refs.viewport.offsetLeft;
      const walk = (x - this.startX) * -1; // invert to match native scroll direction
      this.$refs.viewport.scrollLeft = this.scrollLeftStart + walk;
      this.checkEdge();
    },
    onUp() {
      if (!this.isDown) return;
      this.isDown = false;
      this.$refs.viewport.classList.remove("dragging");
      this.checkEdge();
    },
    onLeave() {
      // when mouse leaves, stop dragging
      if (this.isDown) this.onUp();
    },
    onWheel(e) {
      // allow horizontal wheel (shift or trackpad) + vertical wheel convert
      if (Math.abs(e.deltaX) > 0) {
        // native horizontal
        this.$refs.viewport.scrollLeft += e.deltaX;
      } else {
        // convert vertical wheel to horizontal
        this.$refs.viewport.scrollLeft += e.deltaY;
      }
      this.checkEdge();
    },
    scrollBy(amount) {
      // smooth scroll by px amount
      this.$refs.viewport.scrollBy({ left: amount, behavior: "smooth" });
      // after a short delay check edges (smooth scroll is async)
      setTimeout(this.checkEdge, 250);
    },
    checkEdge() {
      const vp = this.$refs.viewport;
      if (!vp) return;
      this.isAtStart = vp.scrollLeft <= 0;
      // allow small epsilon due to fractional pixels
      this.isAtEnd = Math.ceil(vp.scrollLeft + vp.clientWidth) >= vp.scrollWidth;
    }
  }
};
</script>

<style scoped>
.draggable-slider {
  display: flex;
  align-items: center;
  gap: 8px;
}
.viewport {
  overflow: hidden; /* hide scrollbar */
  flex: 1 1 auto;
  cursor: grab;
  user-select: none;
  -webkit-user-drag: none;
  position: relative;
}
.viewport.dragging {
  cursor: grabbing;
}
.track {
  display: flex;
  gap: var(--gap, 12px);
  align-items: stretch;
  padding: 6px 0;
  /* allow children to be shrink-to-fit horizontally */
}
.slide-item {
  flex: 0 0 auto; /* do not grow or shrink, keep intrinsic width */
  min-width: 160px; /* example width, can be overridden by slot children */
  padding: 12px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.08);
  background: #fff;
  text-align: center;
}
.arrow {
  background: none;
  border: 1px solid #ddd;
  border-radius: 6px;
  padding: 6px 10px;
  cursor: pointer;
}
.arrow:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}
/* hide native scrollbar but still allow interaction */
.viewport::-webkit-scrollbar { height: 8px; }
.viewport::-webkit-scrollbar-thumb { background: rgba(0,0,0,0.15); border-radius: 8px; }
</style>
