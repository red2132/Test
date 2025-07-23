<template>
  <div class="floating-button" @click="handleClick">
    +
  </div>
</template>

<script>
export default {
  name: 'FloatingButton',
  methods: {
    handleClick() {
      this.$emit('click') // 외부로 클릭 이벤트 전달
    }
  }
}
</script>

<style scoped>
.floating-button {
  position: fixed;
  bottom: 24px;
  right: 24px;
  width: 56px;
  height: 56px;
  background-color: #1976d2;
  color: white;
  border-radius: 50%;
  font-size: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  cursor: pointer;
  transition: background-color 0.3s;
}

.floating-button:hover {
  background-color: #115293;
}
</style>
