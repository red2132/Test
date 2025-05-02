<template>
  <div class="card-wrapper">
    <form>
      <div class="field-row">
        <div class="line-header">기본 정보</div>
      </div>
      <div class="field-row">
        <!-- 제휴사 정보 -->
        <label class="field-label">
          제휴사 정보
          <span style="color: #df2428;">＊</span>
        </label>
        <select ref="partner" v-model="formData.prodPartner[0]" style="width: 160px;">
          <option value="" selected disabled>선택</option>
          <option v-for="(combo, index) in comboData.partner" :key="index" :value="combo.value">{{ combo.text }}</option>
        </select>
      </div>
      <!-- 상품명 -->
      <div class="field-row">
        <label class="field-label">상품명<span style="color: #df2428;">＊</span></label>
        <div class="common-input" style="flex: auto;">
          <input type="text" placeholder="상품 이름" style="width: 100%;" v-model="formData.prodNm" />
        </div>
      </div>
      <div class="field-row">
        <!-- 상품 설명 -->
        <label class="field-label">상품설명</label>
        <div class="common-input" style="flex: auto;">
          <input type="text" placeholder="상품 설명" style="width: 100%;" v-model="formData.prodDesc" />
        </div>
      </div>
      <!-- 배너 설명1 -->
      <div class="field-row">
        <label class="field-label">배너 설명 1<span style="color: #df2428;">＊</span></label>
        <div class="common-input" style="flex: auto;">
          <field-c-k-editor v-model="formData.prodContent1" />
        </div>
      </div>
      <!-- 배너 설명2 -->
      <div class="field-row">
        <label class="field-label">배너 설명 2</label>
        <div class="common-input" style="flex: auto;">
          <field-c-k-editor v-model="formData.prodContent2" />
        </div>
      </div>
      <!-- 배너 설명3 -->
      <div class="field-row">
        <label class="field-label">배너 설명 3</label>
        <div class="common-input" style="flex: auto;">
          <field-c-k-editor v-model="formData.prodContent3" />
        </div>
      </div>
      <!-- 카드 신청하기 링크 -->
      <div class="field-row">
        <label class="field-label">카드 신청하기<br />링크<span style="color: #df2428;">＊</span></label>
        <div class="common-input" style="flex: auto;">
          <input type="text" placeholder="카드신청 URL 입력" style="width: 100%;" v-model="formData.productRedirectUrl" />
        </div>
      </div>
      <div class="field-row">
        <label class="field-label">링크 이동 문구<span style="color: #df2428;">＊</span></label>
        <div class="ck-classic">
          <area-c-k-editor v-model="formData.productRedirectMsg" />
        </div>
      </div>
      <!-- 링크 즉시이동 여부 -->
      <div class="field-row">
        <label class="field-label">링크 즉시이동 여부</label>
        <div class="radio-group" style="flex: auto; grid-template-columns: repeat(auto-fill, minmax(10%, auto));">
          <label><input type="radio" name="quickLink" value="Y" v-model="formData.quickLink" @change="changePromotionYn('Y')" />예</label>
          <label><input type="radio" name="quickLink" value="N" v-model="formData.quickLink" @change="changePromotionYn('N')" />아니오</label>
        </div>
      </div>
      <div class="field-row">
        <div class="line-header">카드 정보</div>
      </div>
      <!-- 연회비 -->
      <div class="field-row">
        <label class="field-label">연회비<span style="color: #df2428;">＊</span></label>
        <div class="common-input" style="flex: auto;">
          <label class="field-label">국내<input type="text" v-model="formData.annualFeeLocal" /></label>
          <label class="field-label" style="margin-left: 24px;">해외<input type="text" v-model="formData.annualFeeForeign" /></label>
        </div>
      </div>
      <!-- 혜택 유형 -->
      <div class="field-row">
        <label class="field-label">혜택 유형<span style="color: #df2428;">＊</span></label>
        <div class="checkbox-group" style="flex: auto; grid-template-columns: repeat(auto-fill, minmax(20%, auto));">
          <label v-for="(combo, index) in comboData.benefit" :key="index"><input type="checkbox" name="benefit" :value="combo.value" v-model="formData.optBenefitList" />{{ combo.text }}</label>
        </div>
      </div>
      <!-- 혜택 기간 -->
      <div class="field-row">
        <label class="field-label">혜택 기간</label>
        <div class="common-input" style="flex: auto;">
          <input type="text" placeholder="기간 입력" style="width: 100%;" v-model="formData.benefitSpan" />
        </div>
      </div>
      <!-- 혜택 설명 -->
      <div class="field-row">
        <label class="field-label">혜택 설명<span style="color: #df2428;">＊</span></label>
        <div class="ck-classic">
          <area-c-k-editor v-model="formData.benefitDesc" />
        </div>
      </div>
      <!-- 유의사항 -->
      <div class="field-row">
        <label class="field-label">유의사항<span style="color: #df2428;">＊</span></label>
        <div class="ck-classic">
          <area-c-k-editor v-model="formData.cautionDesc" />
        </div>
      </div>
      <!-- 프로모션 정보 -->
      <div class="field-row">
        <div class="line-header">프로모션 정보</div>
      </div>
      <div class="field-row">
        <label class="field-label">프로모션<br />사용여부</label>
        <div class="radio-group" style="flex: auto; grid-template-columns: repeat(auto-fill, minmax(10%, auto));">
          <label><input type="radio" name="promotionYn" value="Y" v-model="promotionYn" @change="changePromotionYn('Y')" />예</label>
          <label><input type="radio" name="promotionYn" value="N" v-model="promotionYn" @change="changePromotionYn('N')" />아니오</label>
        </div>
      </div>
      <div class="field-row">
        <label class="field-label">프로모션 링크</label>
        <div class="common-input" style="flex: auto;">
          <input ref="promotion" type="text" placeholder="프로모션 URL 입력" style="width: 100%;" :disabled="promotionYn !== 'Y'" v-model="formData.prodOptRedirectUrl" />
        </div>
      </div>
      <div class="field-row">
        <label class="field-label">프로모션 링크<br />이동 문구</label>
        <div class="common-input" style="flex: auto;">
          <input v-if="promotionYn !== 'Y'" type="text" placeholder="URL 이동 문구" style="width: 100%;" disabled v-model="formData.prodOptRedirectMsg" />
          <field-c-k-editor v-else v-model="formData.prodOptRedirectMsg" />
        </div>
      </div>
      <!-- 이미지 정보 -->
      <div class="field-row">
        <div class="line-header">이미지 정보</div>
      </div>
      <!-- 대표 이미지 -->
      <div class="field-row">
        <label class="field-label">대표 이미지<span style="color: #df2428;">＊</span></label>
        <div class="common-input">
          <div>
            <button type="button" @click="() => $refs.productImageFile.click()" class="cms-widget-button" style="width: 140px;"><font-awesome-icon :icon="['fas', 'upload']" size="lg" style="margin-right: 6px;" />이미지 등록</button>
            <input type="file" accept="image/jpeg, image/png" ref="productImageFile" style="display: none;" @change="uploadImage($event, 'productImageFile')" />
            <p style="margin-top: 6px;">※ 이미지 노출 규격 : 126px * 126px / 10MB 이하 / jpeg(jpg), png</p>
            <p style="margin-top: 6px;">※ 고해상도로 제작한 이미지를 등록하여 선명한 화질을 제공해주시기 바랍니다.</p>
          </div>
        </div>
        <div class="image-view">
          <div v-if="previewImageUrl.productImageFile" class="image-preview-section">
            <img :src="previewImageUrl.productImageFile" alt="image" class="image-preview" />
            <font-awesome-icon class="image-remove-x" :icon="['fas', 'x']" size="xl" @click="clearImage('productImageFile')" />
          </div>
        </div>
      </div>
      <!-- QR 이미지 -->
      <div class="field-row">
        <label class="field-label">QR 이미지</label>
        <div class="common-input">
          <div>
            <button type="button" @click="() => $refs.qrImageFile.click()" class="cms-widget-button" style="width: 140px;"><font-awesome-icon :icon="['fas', 'upload']" size="lg" style="margin-right: 6px;" />이미지 등록</button>
            <input type="file" accept="image/jpeg, image/png" ref="qrImageFile" style="display: none;" @change="uploadImage($event, 'qrImageFile')" />
            <p style="margin-top: 6px;">※ 이미지 노출 규격 : 38px * 38px (1 : 1) 이상 / 5MB 이하 / jpeg(jpg), png</p>
          </div>
        </div>
        <div class="image-view">
          <div v-if="previewImageUrl.qrImageFile" class="image-preview-section">
            <img :src="previewImageUrl.qrImageFile" alt="image" class="image-preview qr" />
            <font-awesome-icon class="image-remove-x" :icon="['fas', 'x']" size="xl" @click="clearImage('qrImageFile')" />
          </div>
        </div>
      </div>
      <!-- 프로모션 QR 이미지 -->
      <div class="field-row">
        <label class="field-label">프로모션<br />QR 이미지</label>
        <div class="common-input">
          <div>
            <button type="button" :disabled="promotionYn !== 'Y'" @click="() => $refs.optQrImageFile.click()" class="cms-widget-button" style="width: 140px;"><font-awesome-icon :icon="['fas', 'upload']" size="lg" style="margin-right: 6px;" />이미지 등록</button>
            <input type="file" accept="image/jpeg, image/png" ref="optQrImageFile" style="display: none;" @change="uploadImage($event, 'optQrImageFile')" />
            <p style="margin-top: 6px;">※ 이미지 노출 규격 : 38px * 38px (1 : 1) 이상 / 5MB 이하 / jpeg(jpg), png</p>
          </div>
        </div>
        <div class="image-view">
          <div v-if="previewImageUrl.optQrImageFile" class="image-preview-section">
            <img :src="previewImageUrl.optQrImageFile" alt="image" class="image-preview qr" />
            <font-awesome-icon class="image-remove-x" :icon="['fas', 'x']" size="xl" @click="clearImage('optQrImageFile')" />
          </div>
        </div>
      </div>
      <!-- 기타 정보 -->
      <div class="field-row">
        <div class="line-header">기타 정보</div>
      </div>
      <!-- 전시 여부 -->
      <div class="field-row">
        <label class="field-label">전시 여부</label>
        <div class="radio-group" style="flex: auto; grid-template-columns: repeat(auto-fill, minmax(10%, auto));">
          <label><input type="radio" name="showYn" value="Y" v-model="formData.showYn" />예</label>
          <label><input type="radio" name="showYn" value="N" v-model="formData.showYn" />아니오</label>
        </div>
      </div>
      <!-- 전시 기간 -->
      <div class="field-row">
        <label class="field-label">전시 기간</label>
        <div class="common-input" style="flex: auto;">
          <input type="datetime-local" style="width: 240px;" v-model="formData.productBeginDatetime" />
          <span style="margin: 0 12px; font-size: 1.6rem; align-self: center">~</span>
          <input type="datetime-local" style="width: 240px;" v-model="formData.productFinishDatetime" />
        </div>
      </div>
      <!-- 전시 순위 -->
      <div class="field-row">
        <label class="field-label">전시 순위</label>
        <div class="common-input" style="flex: auto;">
          <input 
            type="number"
            placeholder="숫자"
            style="width: 80px;"
            v-model="formData.ordNo"
            @keydown="inputOrdNoValid(ordNo, 'ordNo')"
            @focusout="inputOrdNoValid(ordNo, 'ordNo')"
          />
        </div>
      </div>
      <!-- 추천 카드 정보 -->
      <div class="field-row">
        <div class="line-header">추천 카드 정보</div>
      </div>
      <!-- 추천 카드 여부 -->
      <div class="field-row">
        <label class="field-label">추천 카드 여부</label>
        <div class="radio-group" style="flex: auto; grid-template-columns: repeat(auto-fill, minmax(10%, auto));">
          <label><input type="radio" name="recoYn" value="Y" v-model="formData.recoYn" />예</label>
          <label><input type="radio" name="recoYn" value="N" v-model="formData.recoYn" />아니오</label>
        </div>
      </div>
      <!-- 추천 카드 전시 순위 -->
      <div class="field-row">
        <label class="field-label">추천 카드 전시 순위</label>
        <div class="common-input" style="flex: auto;">
          <input
            type="number"
            placeholder="숫자"
            style="width: 80px;"
            v-model="formData.recoOrdNo"
            @keydown="inputOrdNoValid(recoOrdNo, 'recoOrdNo')"
            @focusout="inputOrdNoValid(recoOrdNo, 'recoOrdNo')"
          />
        </div>
      </div>
      <!-- 추천카드 제목1 -->
      <div class="field-row">
        <label class="field-label">추천카드 제목 1<span style="color: #df2428;">＊</span></label>
        <div class="common-input" style="flex: auto;">
          <input v-model="formData.recoCardTitle1" @input="(e) => trimRecoCardTitle(e.target.value, 'recoCardTitle1')"/>
          <p style="margin-top: 6px;">※ 10자 이하 권장</p>
        </div>
      </div>
      <!-- 추천카드 제목2 -->
      <div class="field-row">
        <label class="field-label">추천카드 제목 2<span style="color: #df2428;">＊</span></label>
        <div class="common-input" style="flex: auto;">
          <input v-model="formData.recoCardTitle2" @input="(e) => trimRecoCardTitle(e.target.value, 'recoCardTitle2')"/>
          <p style="margin-top: 6px;">※ 10자 이하 권장</p>
        </div>
      </div>
      <!-- 추천카드 제목3 -->
      <div class="field-row">
        <label class="field-label">추천카드 제목 3<span style="color: #df2428;">＊</span></label>
        <div class="common-input" style="flex: auto;">
          <input v-model="formData.recoCardTitle3" @input="(e) => trimRecoCardTitle(e.target.value, 'recoCardTitle3')"/>
          <p style="margin-top: 6px;">※ 10자 이하 권장</p>
        </div>
      </div>
    </form>
    <div class="field-row" style="margin-bottom: 12px;"></div>
  </div>
</template>

<script>
import instance from "@/assets/js/GlobalAxios";
import { b64toBlob } from "@/assets/js/util";
import AreaCKEditor from "@/components/AreaCKEditor.vue";
import FieldCKEditor from "@/components/FieldCKEditor.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

export default {
  components: {
    FontAwesomeIcon,
    FieldCKEditor,
    AreaCKEditor,
  },

  data() {
    return {
      promotionYn: 'N',
      comboData: {
        partner: [],
        benefit: [],
      },
      previewImageUrl: {
        productImageFile: null,
        qrImageFile: null,
        optQrImageFile: null,
      },
      formData: {
        prodPartner: [''],
      },
    };
  },
  setup() {},
  created() {
    this.initialize();
  },
  mounted() {
    this.$emit('load');
  },
  unmounted() {
    // 미리보기 이미지 제거
    for (const k in this.previewImageUrl) {
      URL.revokeObjectURL(this.previewImageUrl[k]);
    }
  },
 /** 초기 데이터 세팅 */
  methods: {
    constructParam: function(param) {
      this.formData = !param // 수정일 경우 기존 데이터 param로 받아옴
          // 입력일 경우
          ? {
            prodId: null,
            ordNo: null,
            prodPartner: [''],
            prodNm: '',
            prodDesc: '',
            prodContent1: '',
            prodContent2: '',
            prodContent3: '',
            signMemo: null,
            quickLink: 'N',
            productRedirectUrl: null,
            productRedirectMsg: null,
            annualFeeLocal: '',
            annualFeeForeign: '',
            optBenefitList: [],
            benefitSpan: '',
            benefitDesc: '',
            cautionDesc: '',
            prodOptRedirectUrl: null,
            prodOptRedirectMsg: null,
            productBeginDatetime: null,
            productFinishDatetime: null,
            showYn: 'Y',
            productImageFile: null,
            qrImageFile: null,
            optQrImageFile: null,
            recoYn: 'N',
            recoOrdNo: null,
            recoCardTitle1: null,
            recoCardTitle2: null,
            recoCardTitle3: null,
          }
          : param;

      if (this.formData.prodOptRedirectUrl) {
        this.promotionYn = 'Y';
      }

      // 이미지 세팅(해당 이미지 없을 경우 함수 종료)
      ['productImageFile', 'qrImageFile', 'optQrImageFile'].forEach((k) => {
        if (!this.formData[k]) {
          return;
        }

        const blob = b64toBlob(this.formData[k]);
        this.previewImage(blob, k);
        this.formData[k] = null;
      });
    },

    /** 제휴사, 혜택 호출 및 세팅 */
    initialize: function() {
      instance.get('/main/common-code', {params: {groupName: 'P_CARD_CD'}})
          .then(async (response) => {
            if (response.responseCode !== 'S') {
              console.warn(response.responseDescription);
              return;
            }

            this.comboData.partner = response.data;
          });

      instance.get('/main/common-code', {params: {groupName: 'OPT_BENEFIT_CD'}})
          .then(async (response) => {
            if (response.responseCode !== 'S') {
              console.warn(response.responseDescription);
              return;
            }

            this.comboData.benefit = response.data;
          });
    },

    /**
     *  이미지 업로드
     * @param {Event} event 이벤트 객체
     * @param {string} key formData에서 변경할 key값
     */
    uploadImage: function(event, key) {
      const file = event.target.files[0];
      if (file) {
        this.formData[key] = file;
        this.previewImage(file, key);
      }
    },

    /**
     * 이미지 제거
     * @param {string} key formData에서 변경할 key값
     */
    clearImage: function(key) {
      this.$refs[key].value = null;
      this.formData[key] = null;
      URL.revokeObjectURL(this.previewImageUrl[key]);
      this.previewImageUrl[key] = null;
    },

    /** 
     * 미리보기 이미지 세팅
     * @param {string} key formData에서 변경할 key값
     */
    previewImage: function(file, key) {
      URL.revokeObjectURL(this.previewImageUrl[key]);
      this.previewImageUrl[key] = URL.createObjectURL(file);
    },

    /** 
     * 순번 숫자 범위 검증
     * @param {number} ordNo 순번 값
     * @param {string} key formData에서 변경할 key값
     */
    inputOrdNoValid: function(ordNo, key) {
      if (!ordNo && typeof ordNo != 'number') {
        return;
      }

      if (ordNo < 1) {
        this.formData[key] = 1;
      }
      if (ordNo > 999) {
        this.formData[key] = 999;
      }
    },

    /** 입력값 검증 */
    confirm: function() {
      if (!this.formData.prodPartner || this.formData.prodPartner.length <= 0) {
        alert('제휴사 정보를 선택해야 합니다.');
        return Promise.reject('제휴사 정보를 선택해야 합니다.');
      }
      if (!this.formData.prodNm) {
        alert('상품명이 비어있습니다.');
        return Promise.reject('상품명이 비어있습니다.');
      }
      if (!this.formData.optBenefitList || this.formData.optBenefitList.length <= 0) {
        alert('혜택 유형을 선택해야 합니다.');
        return Promise.reject('혜택 유형을 선택해야 합니다.');
      }
      if (!this.formData.annualFeeLocal && !this.formData.annualFeeForeign) {
        alert('연회비가 비어있습니다.');
        return Promise.reject('연회비가 비어있습니다.');
      }
      if (!this.formData.prodContent1) {
        alert('설명란 1번 항목이 비어있습니다.');
        return Promise.reject('설명란 1번 항목이 비어있습니다.');
      }
      if (!this.formData.productRedirectUrl) {
        alert('카드 신청하기 URL이 비어있습니다.');
        return Promise.reject('카드 신청하기 URL이 비어있습니다.');
      }
      if (!this.formData.productRedirectMsg) {
        alert('링크 이동 문구가 비어있습니다.');
        return Promise.reject('링크 이동 문구가 비어있습니다.');
      }
      if (this.formData.productBeginDatetime) {
        let date = new Date(this.formData.productBeginDatetime);
        date = new Date(date - date.getTimezoneOffset() * 60000);
        this.formData.productBeginDatetime = date.toISOString().replace('T', ' ').slice(0, -5);
      }
      if (this.formData.productFinishDatetime) {
        let date = new Date(this.formData.productFinishDatetime);
        if (date < Date.now()) {
          alert('전시 기간 종료일시가 현재 시간보다 이전입니다.');
          return Promise.reject('전시 기간 죵료일시가 현재 시간보다 이전입니다.');
        }
        date = new Date(date - date.getTimezoneOffset() * 60000);
        this.formData.productFinishDatetime = date.toISOString().replace('T', ' ').slice(0, -5);
      }
      if (this.formData.productBeginDatetime && this.formData.productFinishDatetime) {
        const begin = new Date(this.formData.productBeginDatetime);
        const finish = new Date(this.formData.productFinishDatetime);

        if (begin > finish) {
          alert('전시 기간 종료일시가 시작일시보다 이전입니다.');
          return Promise.reject('전시 기간 종료일시가 시작일시보다 이전입니다.');
        }
      }


      // 검증 완료된 데이터 전송용 formData로 가공
      const formData = new FormData();
      for (const k in this.formData) {
        if (this.formData[k] !== undefined && this.formData[k] != null && k !== 'rowNo') {
          formData.append(k, this.formData[k]);
        }
      }

      // 데이터 전송
      return new Promise((resolve, reject) => {
        const path = !this.formData.prodId ? '/card/create' : '/card/update';
        instance.post(path, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
            .then((response) => {
              alert(response.responseDescription);
              response.responseCode !== 'S' ? reject(response.responseDescription) : resolve(true);
            });
      });
    },
    /**
     * 프로모션 여부 초기화
     * @param {string} promotionYn 프로모션 여부
     */
    changePromotionYn: function(promotionYn) {
      switch (promotionYn) {
        case 'Y':
          break;

        case 'N':
          this.formData.prodOptRedirectUrl = null;
          this.formData.prodOptRedirectMsg = null;
          this.clearImage('optQrImageFile');
          break;
      }
    },
    /**
     * recoCardTitle 입력값 trim
     * @param {string} title 이벤트 객체
     * @param {string} key formData에서 변경할 key값
     */
    trimRecoCardTitle: function(title, key) {
      this.formData[key] = title.trim();
    },
  },
};
</script>

<style lang="css">
@import "@/assets/css/card.css";
@import "@/assets/css/common.css";
</style>
