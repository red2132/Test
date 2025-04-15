<template>
  <section class="recommend-card-sec">
    <h2 class="recommend-card-title">이달의 추천 제휴카드</h2>
    <div class="swiper-container">
      <swiper
          :modules="modules"
          :slides-per-view="(cards.length >= 6 ? 5.9 : cards.length)"
          :pagination="paginationOptions"

          :spaceBetween="50"
          @swiper="onSwiper"
          :loop="true"
          class="swiper"
        >
        <swiper-slide v-for="(card, index) in cards" :key="index" class="swiper-slide">
            <a style="cursor: pointer;" class="card">
              <div class="card-image"></div>
              <div>
                <span style="font-size: 10px; word-break: keep-all;">{{card.title}}</span>
                <br />
                <span style="font-size: 10px;">{{card.partner}}</span>
              </div>
            </a>
          </swiper-slide>
        </swiper>
        <!-- 커스텀 Pagination + 재생/일시정지 버튼 포함 -->
      <div class="swiper-pagination"></div>
    </div>
  </section>
</template>

<script>
  // import Swiper core and required modules
  import { Pagination, Autoplay } from 'swiper/modules';
  // Import Swiper Vue.js components
  import { Swiper, SwiperSlide } from 'swiper/vue';

  // Import Swiper styles
  import 'swiper/swiper-bundle.css';
import { ref } from 'vue';

  // Import Swiper styles
  export default {
    components: {
      Swiper,
      SwiperSlide,
    },
    setup() {
      const swiperEl = ref(null);
      const onSwiper = (swiper) => {
        swiperEl.value = swiper;
      };
      return {
        swiperEl,
        onSwiper,
        modules: [Pagination, Autoplay],
      };
    },
    data() {
    return {
      autoplayOptions: {
        delay: 2000,
        disableOnInteraction: false,
      },

      paginationOptions: {
        clickable: true,
        el: '.swiper-pagination',
      },

      isPlaying: true,
      activeIndex: 0,
      cards: [
        { title: 'KT DC PLUS', partner: '롯데카드' },
        { title: 'KT SUPER DC 2', partner: '롯데카드' },
        { title: 'KT M Edtion 통신 할인형', partner: '롯데카드' },
        { title: 'KT DC PLUS', partner: '롯데카드' },
        { title: 'KT SUPER DC 2', partner: '롯데카드' },
        { title: 'KT M Edtion 통신 할인형', partner: '롯데카드' },
        { title: 'KT DC PLUS', partner: '롯데카드' },
        { title: 'KT SUPER DC 2', partner: '롯데카드' },
      ],
    };
  },
};
</script>
<style scoped>
.recommend-card-sec {
  width: 524px;
  height: 260px;
  position: relative;
  background-color: rgba(129, 156, 255, 0.1);
}

.recommend-card-title {
  height: 32px;
  color: black;
  font-size: 22px;
  font-weight: 700;
  line-height: 32px;
  text-align: center;
  white-space: nowrap;
  letter-spacing: -0.5px;
  padding: 20px 0px 0px 0px;
  margin-bottom: 42px;
}

.swiper-container {
  width: 569px;
  height: 176px;
  display: flex;
  overflow: hidden;
}

.swiper {
  position: relative;
  left: -45px; /* 왼쪽으로 15px 이동 */
}

.card {
  width: 90px;
  height: 176px;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
  background-color: yellow;
}

.card-image {
  width: 68px;
  height: 100px;
  object-fit: cover;
  border-radius: inherit;
  background-color: red;
}

.swiper-pagination {
  position: absolute;
  transform: none;
  z-index: 2;
  display: flex;
  justify-content: center;
}

</style>
