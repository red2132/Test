<template>
  <div class="range-slider" @pointerdown.prevent="">
    <div class="track" ref="trackRef">
      <!-- 선택된 범위 -->
      <div
        class="range"
        :style="{ left: rangeLeft + '%', width: rangeWidth + '%' }"
      ></div>

      <!-- 최소 핸들 -->
      <button
        class="thumb thumb-left"
        :style="{ left: leftPercent + '%' }"
        ref="leftThumb"
        role="slider"
        :aria-valuemin="min"
        :aria-valuemax="max"
        :aria-valuenow="leftValue"
        aria-label="Minimum value"
        @pointerdown="startDrag('min', $event)"
        @keydown="onKeydown('min', $event)"
      ></button>

      <!-- 최대 핸들 -->
      <button
        class="thumb thumb-right"
        :style="{ left: rightPercent + '%' }"
        ref="rightThumb"
        role="slider"
        :aria-valuemin="min"
        :aria-valuemax="max"
        :aria-valuenow="rightValue"
        aria-label="Maximum value"
        @pointerdown="startDrag('max', $event)"
        @keydown="onKeydown('max', $event)"
      ></button>
    </div>

    <div class="labels">
      <div class="label">Min: {{ leftValue }}</div>
      <div class="label">Max: {{ rightValue }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';

const props = defineProps({
  modelValue: Array,
  min: { type: Number, default: 0 },
  max: { type: Number, default: 100 },
  step: { type: Number, default: 1 }
});

const emit = defineEmits(['update:modelValue']);

const min = props.min;
const max = props.max;
const step = props.step;

const leftValueRef = ref(props.modelValue?.[0] ?? min);
const rightValueRef = ref(props.modelValue?.[1] ?? max);

watch(
  () => props.modelValue,
  (v) => {
    if (!v) return;
    leftValueRef.value = clamp(v[0], min, max);
    rightValueRef.value = clamp(v[1], min, max);
  },
  { immediate: true }
);

watch([leftValueRef, rightValueRef], () => {
  if (leftValueRef.value > rightValueRef.value) {
    const a = Math.min(leftValueRef.value, rightValueRef.value);
    const b = Math.max(leftValueRef.value, rightValueRef.value);
    leftValueRef.value = a;
    rightValueRef.value = b;
  }
  emit('update:modelValue', [leftValueRef.value, rightValueRef.value]);
});

const leftValue = computed(() => leftValueRef.value);
const rightValue = computed(() => rightValueRef.value);

const leftPercent = computed(() => valueToPercent(leftValue.value, min, max));
const rightPercent = computed(() => valueToPercent(rightValue.value, min, max));
const rangeLeft = computed(() => leftPercent.value);
const rangeWidth = computed(() => rightPercent.value - leftPercent.value);

const trackRef = ref(null);
let active = null;

function startDrag(which, ev) {
  active = which;
  ev.target.setPointerCapture(ev.pointerId);
  window.addEventListener('pointermove', onMove);
  window.addEventListener('pointerup', onUp);
}

function onMove(ev) {
  if (!active || !trackRef.value) return;

  const rect = trackRef.value.getBoundingClientRect();
  const pct = ((ev.clientX - rect.left) / rect.width) * 100;
  const value = percentToValue(pct, min, max, step);

  if (active === 'min') {
    leftValueRef.value = Math.min(value, rightValueRef.value);
  } else {
    rightValueRef.value = Math.max(value, leftValueRef.value);
  }
}

function onUp() {
  window.removeEventListener('pointermove', onMove);
  window.removeEventListener('pointerup', onUp);
  active = null;
}

function onKeydown(which, ev) {
  let delta = 0;
  if (ev.key === 'ArrowLeft' || ev.key === 'ArrowDown') delta = -step;
  if (ev.key === 'ArrowRight' || ev.key === 'ArrowUp') delta = step;
  if (ev.key === 'PageUp') delta = step * 10;
  if (ev.key === 'PageDown') delta = -step * 10;

  if (!delta) return;

  ev.preventDefault();

  if (which === 'min') {
    leftValueRef.value = clamp(leftValueRef.value + delta, min, rightValueRef.value);
  } else {
    rightValueRef.value = clamp(rightValueRef.value + delta, leftValueRef.value, max);
  }
}

/* utils */
function clamp(v, a, b) {
  return Math.min(Math.max(v, a), b);
}

function valueToPercent(v, min, max) {
  return ((v - min) / (max - min)) * 100;
}

function percentToValue(p, min, max, step) {
  const raw = min + ((Math.min(Math.max(p, 0), 100) / 100) * (max - min));
  return Math.round(raw / step) * step;
}
</script>

<style scoped>
.range-slider {
  width: 100%;
  max-width: 520px;
  user-select: none;
}

.track {
  position: relative;
  height: 10px;
  margin: 16px 0;
  background: #e6e6e6;
  border-radius: 999px;
}

.range {
  position: absolute;
  top: 0;
  bottom: 0;
  border-radius: 999px;
  background: #3b82f6;
}

.thumb {
  position: absolute;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: white;
  border: 2px solid #999;
  cursor: grab;
  touch-action: none;
}
.thumb:active {
  cursor: grabbing;
}
.labels {
  display: flex;
  justify-content: space-between;
}
.label {
  padding: 4px 8px;
  background: #f1f1f1;
  border-radius: 4px;
}
</style>
