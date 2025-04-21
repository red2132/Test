<template>
  <section class="recommend-card-sec">
    <h2 class="recommend-card-title">이달의 추천 제휴카드</h2>
    <div class="swiper-container">
      <div class="swiper-wrapper">
        <swiper
        :modules="modules"
        :slides-per-view="6"
        :pagination="paginationOptions"
        :autoplay="autoplayOptions"
        :space-between="50"
        :loop="true"
        @swiper="onSwiper"
        class="swiper"
      >
        <swiper-slide v-for="(card, index) in adjustedCards" :key="`${index}-${card.title}`" class="swiper-slide">
          <a class="card">
            <div class="card-image"></div>
            <div>
              <span style="font-size: 10px; word-break: keep-all;">{{ card.title }}</span>
              <br />
              <span style="font-size: 10px;">{{ card.partner }}</span>
            </div>
          </a>
        </swiper-slide>
      </swiper>
      </div>
    </div>
    <div class="swiper-controls">
      <button @click="pauseAutoplay">정지</button>
      <button @click="resumeAutoplay">재생</button>
    </div>
  </section>
  </template>
  
  <script>
  import { Pagination, Autoplay } from 'swiper/modules';
  import { Swiper, SwiperSlide } from 'swiper/vue';
  import { ref } from 'vue';
  import 'swiper/swiper-bundle.css';
  
  export default {
    components: {
      Swiper,
      SwiperSlide
    },
    setup() {
      const swiperEl = ref(null);
  
      const autoplayOptions = {
        delay: 2000,
        disableOnInteractions: false
      };
      
      const paginationOptions = {
        clickable: true,
        el: '.swiper-pagination'
      };
  
      const onSwiper = (swiper) => {
        swiperEl.value = swiper;
      };

      const pauseAutoplay = () => {
        if (swiperEl.value && swiperEl.value.autoplay) {
          swiperEl.value.autoplay.stop();
        }
      };

      const resumeAutoplay = () => {
        if (swiperEl.value && swiperEl.value.autoplay) {
          swiperEl.value.autoplay.start();
        }
      };
  
      return{
        swiperEl,
        autoplayOptions,
        paginationOptions,
        onSwiper,
        pauseAutoplay,
        resumeAutoplay,
        modules: [Pagination, Autoplay]
      }
    },
    data() {
      return {
        cards: [
          { title: 'KT DC PLUS', partner: '롯데카드' },
          { title: 'KT SUPER DC 2', partner: '삼성카드' },
          { title: 'KT M Edtion 통신 할인형', partner:'롯데카드' },
          { title: 'KT DC PLUS333', partner: '롯데카드' },
        ]
      }
    },
    computed: {
      adjustedCards() {
        const len = this.cards.length;
        let repeat = 1;

        if (len === 1) {
          repeat = 7;
        } else if (len === 2) {
          repeat = 4;
        } else if (len === 3) {
          repeat = 3;
        } else if (len <= 6) {
          repeat = 2;
        }

        return Array.from({ length: repeat }, () =>
          this.cards.map(card => JSON.parse(JSON.stringify(card)))
        ).flat();
      }
    }
  }
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
    padding: 15px 0px 0px 0px;
    margin-bottom: 32px;
  }
  
  .swiper-container {
    width: 569px;
    height: 176px;
    display: flex;
    overflow: hidden;
  }
  
  .swiper {
    position: relative;
    left: -45px;
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
  
