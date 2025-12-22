<template>
  <div class="category-slider-wrapper">
    <swiper
      :modules="modules"
      @swiper="onSwiper"
      slides-per-view="auto"
      :space-between="10"
      :scrollbar="scrollbarOptions"
    >
      <swiper-slide
        v-for="category in categoryList"
        :key="category.partnerCode"
        class="category-slide"
      >
        <button
          type="button"
          :class="{
            'label-category-company-active': modelValue === category.partnerCode,
          }"
          @click="selectCategory(category.partnerCode)"
          :title="modelValue === category.partnerCode ? '선택됨' : null"
        >
          <span class="label-category-company">{{ category.partnerNm }}</span>
        </button>
      </swiper-slide>
      <div class="swiper-scrollbar"></div>
    </swiper>
  </div>
</template>

<script>
import { Scrollbar } from "swiper/modules";
import "swiper/swiper-bundle.css";
import { Swiper, SwiperSlide } from "swiper/vue";
import { ref } from "vue";

export default {
  name: "CategorySlider",

  props: {
    categoryList: Array,
    modelValue: String,
    isRouteParam: Boolean,
  },

  emits: ["update:modelValue"],

  components: {
    Swiper,
    SwiperSlide,
  },

  setup() {
    const swiperEl = ref(null);

    const onSwiper = (swiper) => {
      swiperEl.value = swiper;
    };

    const scrollbarOptions = {
      el: ".swiper-scrollbar",
      draggable: true,
      hide: true,
    };

    return {
      swiperEl,
      onSwiper,
      scrollbarOptions,
      modules: [Scrollbar],
    };
  },

  watch: {
    categoryList() {
      this.setRouteParamCategory();
    },
    swiperEl() {
      this.setRouteParamCategory();
    },
  },

  methods: {
    /**
     * 카테고리 선택
     * @param {String} category
     */
    selectCategory(category) {
      this.$emit("update:modelValue", category);
    },

    /**
     * route param으로 카테고리 코드를 받을 경우, 해당 카테고리 슬라이드로 이동
     */
    setRouteParamCategory() {
      if (
        this.isRouteParam !== null &&
        this.swiperEl.value !== null &&
        this.categoryList.length > 1
      ) {
        const index = this.categoryList.findIndex((item) => item.partnerCode === this.modelValue);

        if (index > -1) {
          this.swiperEl.slideTo(index);
        }
      }
    },
  },
};
</script>

<style scoped>
.category-slider-wrapper {
  width: 524px;
  margin-top: 12px;
  padding: 0px 16px;
}
.category-slide {
  width: auto;
}
.swiper {
  padding-bottom: 16px;
}
</style>
