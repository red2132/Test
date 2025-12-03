<template>
  <div class="slider-wrapper">
    <div
      ref="track"
      class="slider-track"
      @mousedown="startDrag"
      @touchstart.passive="startDrag"
      @mousemove="onDrag"
      @touchmove.passive="onDrag"
      @mouseup="endDrag"
      @mouseleave="endDrag"
      @touchend="endDrag"
      @touchcancel="endDrag"
    >
      <!-- 슬롯으로 자식 요소 받기 -->
      <slot>
        <!-- 기본 예시 아이템들 (원하면 제거) -->
        <div class="item" v-for="n in 10" :key="n">Item {{ n }}</div>
      </slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "HorizontalDragSlider",
  data() {
    return {
      isDragging: false,
      startX: 0,
      startScrollLeft: 0,
      lastTouchId: null,
    };
  },
  methods: {
    // 드래그 시작
    startDrag(e) {
      this.isDragging = true;
      // 마우스 이벤트일 때
      if (e.type === "mousedown") {
        this.startX = e.pageX;
      } else {
        // touchstart: 사용된 터치의 identifier 를 기억
        const t = e.touches ? e.touches[0] : e;
        this.lastTouchId = t && t.identifier;
        this.startX = t.pageX;
      }
      this.startScrollLeft = this.$refs.track.scrollLeft;
      // 드래그 중 텍스트 선택 방지
      document.body.classList.add("no-select");
    },

    // 드래그 중
    onDrag(e) {
      if (!this.isDragging) return;
      let pageX;
      if (e.type.startsWith("mouse")) {
        pageX = e.pageX;
      } else {
        // touchmove: 같은 터치 찾기
        const touches = e.touches;
        if (!touches || touches.length === 0) return;
        // 기본적으로 첫번째 터치 사용
        pageX = touches[0].pageX;
      }

      const delta = pageX - this.startX;
      // 반대방향으로 스크롤 (드래그 오른쪽 -> scrollLeft 감소)
      this.$refs.track.scrollLeft = this.startScrollLeft - delta;
    },

    // 드래그 끝
    endDrag() {
      if (!this.isDragging) return;
      this.isDragging = false;
      this.lastTouchId = null;
      document.body.classList.remove("no-select");
    },
  },
  mounted() {
    // 데스크탑에서 마우스 버튼을 떼는 이벤트를 전역으로도 잡아 안전하게 종료
    window.addEventListener("mouseup", this.endDrag);
    window.addEventListener("touchend", this.endDrag);
    window.addEventListener("touchcancel", this.endDrag);
  },
  beforeDestroy() {
    window.removeEventListener("mouseup", this.endDrag);
    window.removeEventListener("touchend", this.endDrag);
    window.removeEventListener("touchcancel", this.endDrag);
  },
};
</script>

<style scoped>
.slider-wrapper {
  width: 100%;
  overflow: hidden; /* 가시 영역만 보이게 */
}

/* 트랙: 가로 한 줄로 정렬 */
.slider-track {
  display: flex;
  gap: 12px;
  padding: 12px;
  overflow-x: auto;
  scroll-behavior: smooth;
  -webkit-overflow-scrolling: touch;
  cursor: grab;
  user-select: none; /* 기본 선택 방지 */
}

/* 드래그 중 cursor 변경 */
.slider-track:active {
  cursor: grabbing;
}

/* 스크롤바 숨기기 (크로스브라우저) */
.slider-track::-webkit-scrollbar {
  display: none;
}
.slider-track {
  -ms-overflow-style: none; /* IE, Edge */
  scrollbar-width: none; /* Firefox */
}

/* 기본 아이템 스타일 (예시) */
.item {
  min-width: 160px;
  height: 100px;
  background: #f3f4f6;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0; /* 축소 방지 */
  box-shadow: 0 1px 3px rgba(0,0,0,0.08);
}

/* 문서 전체에서 드래그 시 텍스트 선택 금지 클래스 */
.no-select {
  -webkit-user-select: none !important;
  -moz-user-select: none !important;
  -ms-user-select: none !important;
  user-select: none !important;
}
</style>
