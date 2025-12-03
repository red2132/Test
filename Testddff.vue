<template>
  <div class="slider" ref="slider" @mousedown="down" @mousemove="move" @mouseup="up" @mouseleave="up" @touchstart="down" @touchmove="move" @touchend="up">
    <div class="track">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pressed: false,
      startX: 0,
      scrollStart: 0
    };
  },
  methods: {
    down(e) {
      this.pressed = true;
      const clientX = e.touches ? e.touches[0].clientX : e.clientX;
      this.startX = clientX;
      this.scrollStart = this.$refs.slider.scrollLeft;
    },
    move(e) {
      if (!this.pressed) return;
      const clientX = e.touches ? e.touches[0].clientX : e.clientX;
      const walk = clientX - this.startX;
      this.$refs.slider.scrollLeft = this.scrollStart - walk;
    },
    up() {
      this.pressed = false;
    }
  }
};
</script>

<style scoped>
.slider {
  overflow-x: auto;
  white-space: nowrap;
  cursor: grab;
}
.track {
  display: inline-flex;
  gap: 8px;
}
.slider::-webkit-scrollbar { display: none; }
</style>
