<template>
  <div class="field">
    <div class="top-container px-5 py-5">
      <div class="profile-logo">
        <div style="text-align: left">
          <h2>간단번역</h2>
        </div>
        <div style="margin-left: 41%">
          <h2 @click="$router.push('/')">PAGO BOOKS</h2>
        </div>
      </div>
      <!--profile-logo-end-->
    </div>
    <!--top-container-end-->
    <div class="papago-bottom-container px-5 pt-5 pb-2">
      <div class="ppg-container">
        <div class="ppg-input-cont">
          <div class="ts-lg-ch">
            <select
              v-model="from_language"
              name="ts-lg"
              id="ts-lg1"
              onchange="categoryChange(this)"
            >
              <option value="ko">한국어</option>
              <option value="en">영어</option>
              <option value="ja">일본어</option>
              <option value="zh-CN">중국어</option>
              <option value="de">독일어</option>
              <option value="es">스페인어</option>
            </select>
          </div>
          <div>
            <textarea
              v-model="input"
              class="ppg-ts-box"
              placeholder="번역할 내용을 입력하세요"
            ></textarea>
          </div>
          <div class="ppg-trans-btn mt-4">
            <button @click="translation">번역하기</button>
          </div>
        </div>
        <!--ts-input-cont-end-->
        <div class="ppg-refresh">
          <img src="../assets/papagoRefresh.png" @click="change" />
        </div>
        <div class="ppg-output-cont">
          <div class="ts-lg-ch">
            <select v-model="to_language" name="ts-lg" id="ts-lg2">
              <option value="ko">한국어</option>
              <option value="en">영어</option>
              <option value="ja">일본어</option>
              <option value="zh-CN">중국어</option>
              <option value="de">독일어</option>
              <option value="es">스페인어</option>
            </select>
          </div>
          <div class="ppg-ts-box" style="margin-bottom: 30px">
            <form action="#">
              <output name="result" for="text">
                <div v-html="content"></div>
              </output>
            </form>
          </div>
          <div class="ppg-save-btn mt-4">
            <div v-show="isLogin">
              <div class="ppg-save-btn mt-4">
                <button @click="upload">저장하기</button>
              </div>
            </div>
          </div>
        </div>
        <!--ts-output-cont-end-->
      </div>
      <!--ppg-container-end-->

      <br />

      <div
        style="
          text-align: center;
          font-weight: bold;
          margin-top: 11px;
          margin-bottom: 11px;
        "
      >
        <p>[ Tip : 로그인을 하시면 자료를 보관하고 내려받을 수 있습니다 ]</p>
      </div>
    </div>
    <!--papago-bottom-container-end-->
  </div>
  <!--field_end-->
</template>

<script>
// import $ from 'jquery'
import axios from "axios";
export default {
  name: "papagoPage",
  data() {
    return {
      input: "",
      output: "",
      from_language: "ko",
      to_language: "en",
    };
  },
  methods: {
    change() {
      let temp = this.input;
      this.input = this.output;
      this.output = temp;
      temp = this.from_language;
      this.from_language = this.to_language;
      this.to_language = temp;
    },
    async upload() {
      console.log(this.output);
      if (
        this.input != null &&
        this.output != null &&
        this.input !== "" &&
        this.output !== ""
      ) {
        let form = new FormData();
        form.append("email", this.$store.state.userInfo.email);
        var date = new Date();

        form.append(
          "trans_date",
          new Date(
            date.getTime() - date.getTimezoneOffset() * 60000
          ).toISOString()
        );
        form.append("input", this.input);
        form.append("output", this.output);

        this.$store.dispatch("setLoading", true);
        await axios
          .post("/api/papago/upload", form, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((res) => {
            console.log(res);
            alert("저장이 완료되었습니다.");
          })
          .catch((err) => {
            console.log("refreshToken error : ", err.config);
          });
        this.$store.dispatch("setLoading", false);
      }
    },
    async translation() {
      let form = new FormData();
      form.append("text", this.input);
      form.append("from_language", this.from_language);
      form.append("to_language", this.to_language);

      if (this.from_language == this.to_language) {
        this.output = this.input;
        return;
      }
      if (this.from_language == "ja" && this.to_language == "es") {
        alert("잘못된 요청입니다.");
        this.output = this.input;
        return;
      }

      if (this.from_language == "zh-CN" && this.to_language == "es") {
        alert("잘못된 요청입니다.");
        this.output = this.input;
        return;
      }
      if (this.from_language == "zh-CN" && this.to_language == "de") {
        alert("잘못된 요청입니다.");
        this.output = this.input;
        return;
      }
      if (this.from_language == "de" && this.to_language == "ja") {
        alert("잘못된 요청입니다.");
        this.output = this.input;
        return;
      }
      if (this.from_language == "de" && this.to_language == "zh-CN") {
        alert("잘못된 요청입니다.");
        this.output = this.input;
        return;
      }
      if (this.from_language == "de" && this.to_language == "es") {
        alert("잘못된 요청입니다.");
        this.output = this.input;
        return;
      }
      if (this.from_language == "es" && this.to_language == "ja") {
        alert("잘못된 요청입니다.");
        this.output = this.input;
        return;
      }
      if (this.from_language == "es" && this.to_language == "zh-CN") {
        alert("잘못된 요청입니다.");
        this.output = this.input;
        return;
      }
      if (this.from_language == "es" && this.to_language == "de") {
        alert("잘못된 요청입니다.");
        this.output = this.input;
        return;
      }

      this.$store.dispatch("setLoading", true);
      await axios
        .post("/api/papago/json", form, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          console.log(res);
          if (res.data.includes("errorCode:N2MT06")) {
            alert("지원하지 않는 번역기입니다.");
            return;
          }
          if (res.data.includes("errorCode:N2MT08")) {
            alert("번역기 용량이 초과되었습니다.");
            return;
          }
          if (res.data.includes("errorCode:010")) {
            alert("파파고 사용제한이 초과되었습니다.");
            return;
          }
          this.output = res.data;
        })
        .catch((err) => {
          console.log("refreshToken error : ", err.config);
        });
      this.$store.dispatch("setLoading", false);
    },
  },
  components: {},
  computed: {
    content() {
      return this.output.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    isLogin() {
      return this.$store.state.isLogin;
    },
  },
  mounted() {},
};
</script>

<style>
body {
  margin: 0;
}

textarea {
  resize: none;
}

.profile-logo > div > h2 {
  color: white;
  font-weight: bold;
}

.bottom-container {
  background: white;
  border-radius: 100px 0px 0px 0px;
}

.papago-bottom-container {
  background: white;
  border-radius: 100px 0px 0px 0px;
}

.ppg-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

.top-content {
  display: flex;
  justify-content: space-between;
  padding: 2rem 4rem 2rem 4rem;
  color: white;
}

.ppg-output-cont {
  width: 40%;
  margin: 1rem;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
}

.ts-lg-ch {
  text-align: left;
  margin-bottom: 1.5rem;
}

.ts-lg-ch > select {
  font-size: 1rem;
  padding: 0.5rem;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
}

select:hover {
  border-color: none;
}

select:focus {
  outline: none;
}

.ppg-input-cont {
  width: 40%;
  height: 100%;
  margin: 1rem;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
  text-align: center;
}

.ppg-ts-box {
  width: 100%;
  height: 350px;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  padding: 1rem;
  text-align: left;
  overflow: auto;
}
.ppg-ts-box::-webkit-scrollbar {
  display: none;
}
.ppg-trans-btn > button {
  width: 100%;
  padding: 0.5rem;
  background: #0d66ff;
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: bold;
}

.ppg-save-btn > button {
  width: 100%;
  padding: 0.5rem;
  background: white;
  color: #0d66ff;
  border: 1px solid #0d66ff;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: bold;
}

.ppg-output-cont {
  width: 40%;
  margin: 1rem;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
}

.output-record {
  text-align: end;
}

.output-record > button {
  text-align: center;
  width: 20%;
  padding: 1.25rem;
  background: #0d66ff;
  border-radius: 50px 0px 0px 0px;
}

.ppg-refresh {
  line-height: 550px;
}

.ppg-refresh > img {
  width: 50px;
  height: 50px;
  margin: 0rem 1rem 0rem 1rem;
  vertical-align: middle;
  cursor: pointer;
}
</style>