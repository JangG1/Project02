<template>
  <div class="field">
    <div class="top-container px-5 py-5">
      <div class="profile-logo">
        <div>
          <h2>음성변환</h2>
        </div>
        <div style="margin-left: 41%">
          <h2 @click="$router.push('/')">PAGO BOOKS</h2>
        </div>
        <!-- <div @click="loginModal = true">
            <img src="@/assets/weblogin1.png" alt="profile-logo" />
          </div> -->
      </div>
      <!--profile-logo-end-->
    </div>
    <!--top-container-end-->

    <div class="tts-bottom-container px-5 pt-5 pb-2">
      <div class="tts-ts-container">
        <div class="tts-output-cont">
          <div>
            <textarea
              class="tts-ts-box"
              v-model="text"
              placeholder="
                        
                        여기에 만들고 싶은 음성의 내용을 작성하세요.
      
                        ■ 유용한 Tip~
                        ① 마침표, 쉼표, 띄어쓰기를 꼭 사용하세요. 쉼표를 적절히 자주 붙이면 내용이 명확해집니다.
                        ② 마침표를 제외한 특수문자는 사용하지 마세요. 몇가지 음성에서만 물음표 사용이 가능합니다.
                        ③ 1000자 내외로 입력해주세요."
            >
            </textarea>
          </div>
          <div style="display: flex; justify-content: space-between">
            <!--By.지원_보이스선택-->
            <div style="display: flex">
              <div class="voice">음성 종류</div>
              <select name="voice" v-model="voice" class="voiceSelect">
                <option value="">--음성을 선택해주세요--</option>
                <option value="nara">아라 한국어 여성</option>
                <option value="nara_call">아라(상담원) 한국어 여성</option>
                <option value="nminyoung">민영 한국어 여성</option>
                <option value="nyejin">예진 한국어 여성</option>
                <option value="mijin">미진 한국어 여성</option>
                <option value="jinho">진호 한국어 남성</option>
                <option value="clara">클라라 영어 여성</option>
                <option value="matt">매트 영어 남성</option>
                <option value="shinji">신지 일본어 남성</option>
                <option value="meimei">메이메이 중국어 여성</option>
                <option value="liangliang">량량 중국어 남성</option>
                <option value="jose">호세 스페인어 남성</option>
                <option value="carmen">카르멘 스페인어 여성</option>
                <option value="nminsang">민상 한국어 남성</option>
                <option value="nsinu">신우 한국어 남성</option>
                <option value="nhajun">하준 한국어 남성(아동)</option>
                <option value="ndain">다인 한국어 여성(아동)</option>
                <option value="njiyun">지윤 한국어 여성</option>
                <option value="nsujin">수진 한국어 여성</option>
                <option value="njinho">진호 한국어 남성</option>
                <option value="njihun">지훈 한국어 남성</option>
                <option value="njooahn">주안 한국어 남성</option>
                <option value="nseonghoon">성훈 한국어 남성</option>
                <option value="njihwan">지환 한국어 남성</option>
                <option value="nsiyoon">시윤 한국어 남성</option>
                <option value="ngaram">가람 한국어 남성</option>
                <option value="ntomoko">토모코 일본어 여성</option>
                <option value="nnaomi">나오미 일본어 여성</option>
                <option value="dnaomi_joyful">나오미(기쁨) 일본어 여성</option>
                <option value="dnaomi_formal">나오미(뉴스) 일본어 여성</option>
                <option value="driko">리코 일본어 여성</option>
                <option value="deriko">에리코 일본어 여성</option>
                <option value="nsayuri">사유리 일본어 여성</option>
                <option value="ngoeun">고은 한국어 여성</option>
                <option value="neunyoung">은영 한국어 여성</option>
                <option value="nsunkyung">선경 한국어 여성</option>
                <option value="nyujin">유진 한국어 여성</option>
                <option value="ntaejin">태진 한국어 남성</option>
                <option value="nyoungil">영일 한국어 남성</option>
                <option value="nseungpyo">승표 한국어 남성</option>
                <option value="nwontak">원탁 한국어 남성</option>
                <option value="dara_ang">아라(화남) 한국어 여성</option>
                <option value="nsunhee">선희 한국어 여성</option>
                <option value="nminseo">민서 한국어 여성</option>
                <option value="njiwon">지원 한국어 여성</option>
                <option value="nbora">보라 한국어 여성</option>
                <option value="njonghyun">종현 한국어 남성</option>
                <option value="njoonyoung">준영 한국어 남성</option>
                <option value="njaewook">재욱 한국어 남성</option>
                <option value="danna">안나 영어 여성</option>
                <option value="djoey">조이 영어 여성</option>
                <option value="dhajime">하지메 일본어 남성</option>
                <option value="ddaiki">다이키 일본어 남성</option>
                <option value="dayumu">아유무 일본어 남성</option>
                <option value="dmio">미오 일본어 여성</option>
                <option value="chiahua">차화 대만어 여성</option>
                <option value="kuanlin">관린 대만어 남성</option>
                <option value="dnaomi_joyful">나오미(기쁨) 일본어 여성</option>
                <option value="dnaomi_formal">나오미(뉴스) 일본어 여성</option>
                <option value="driko">리코 일본어 여성</option>
                <option value="deriko">에리코 일본어 여성</option>
                <option value="nes_c_hyeri">혜리 한국어 여성</option>
                <option value="nes_c_sohyun">소현 한국어 여성</option>
                <option value="nes_c_mikyung">미경 한국어 여성</option>
                <option value="nes_c_kihyo">기효 한국어 남성</option>
              </select>
              <!--By.지원_속도조절-->
              <div class="speed">속도</div>
              <select name="voiceSpeed" v-model="speed" class="speedSelect">
                <option value="-5">-5</option>
                <option value="-4">-4</option>
                <option value="-3">-3</option>
                <option value="-2">-2</option>
                <option value="-1">-1</option>
                <option value="0" selected>0</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
              </select>
              <!--By.지원_볼륨조절-->
              <div class="volume">볼륨</div>
              <select name="voiceVolume" v-model="volume" class="volumeSelect">
                <option value="-5">-5</option>
                <option value="-4">-4</option>
                <option value="-3">-3</option>
                <option value="-2">-2</option>
                <option value="-1">-1</option>
                <option value="0" selected>0</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
              </select>
            </div>
            <!--By.지원_전송버튼-->
            <div class="tts-change-btn">
              <button type="button" @click="sendData() + div_show()">
                음성 변환하기
              </button>
            </div>
          </div>
          <hr id="line" />
          <!--결과-->
          <div style="display: flex; justify-content: space-between">
            <!--By.지원_저장하기버튼-->
            <div v-show="isLogin">
              <div class="tts-save-btn mt-3" id="save">
                <button @click="upload">저장하기</button>
              </div>
            </div>
            <div class="tts-output-box mt-3">
              <!--By.지원_플레이어-->
              <audio
                controls=""
                controlsList="nodownload"
                id="audio"
                ref="player"
              >
                <source src="" type="audio/mpeg" ref="source" />
              </audio>
              <!--By.지원_MP3다운로드-->
              <div class="download" id="dlButton">
                <button type="button" value="page move" @click="download()">
                  MP3 다운로드
                </button>
              </div>
            </div>
            <!--tts-output-box-end-->
          </div>
        </div>
        <br />
      </div>
      <!--tts-bottom-container-end-->
      <div
        style="
          text-align: center;
          font-weight: bold;
          margin-top: 22px;
          margin-bottom: 22px;
        "
      >
        <p>[ Tip : 로그인을 하시면 자료를 보관하고 내려받을 수 있습니다 ]</p>
      </div>
    </div>
    <!--field_end-->
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "papagoPage",
  data() {
    return {
      text: "",
      voice: "nara",
      speed: "0",
      volume: "0",
    };
  },
  components: {},
  methods: {
    async upload() {
      if (this.text == null || this.text === "") {
        alert("데이터를 입력해주세요.");
        return;
      }
      let form = new FormData();
      form.append("email", this.$store.state.userInfo.email);
      var date = new Date();

      form.append(
        "trans_date",
        new Date(
          date.getTime() - date.getTimezoneOffset() * 60000
        ).toISOString()
      );
      form.append("kind", "voice");
      form.append("input", this.text);
      this.$store.dispatch("setLoading", true);
      await axios

        .post("/api/tts/upload", form, {
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
    },

    //보이기
    div_show() {
      if ("" != this.text) {
        document.getElementById("audio").style.display = "block";
        document.getElementById("dlButton").style.display = "block";
        document.getElementById("save").style.display = "block";
        document.getElementById("line").style.display = "block";
      }
    },
    //숨기기
    div_hide() {
      document.getElementById("audio").style.display = "none";
      document.getElementById("dlButton").style.display = "none";
      document.getElementById("save").style.display = "none";
      document.getElementById("line").style.display = "none";
    },
    async sendData() {
      if ("" === this.text) {
        alert("값을 입력해주세요.");
        this.$route.go("/tts");
      } else {
        let str = "/api/tts";
        this.$store.dispatch("setLoading", true);
        await axios
          .post(
            str,
            {
              data1: this.text,
              data2: this.voice,
              data3: this.speed,
              data4: this.volume,
            },
            {
              responseType: "blob",
            }
          )
          .then((res) => {
            const url = window.URL.createObjectURL(new Blob([res.data]));
            this.$refs.source.src = url;
            this.$refs.player.load();
          });
        this.$store.dispatch("setLoading", false);
      }
    },

    // sendData() {
    //     if ("" === this.text) {
    //         alert("값을 입력해주세요.");
    //         location.href = "http://localhost:8081/tts";
    //     } else {
    //         axios({
    //             url: "api/tts/server",
    //             method: "post",
    //             data: {
    //                 data1: this.text,
    //                 data2: this.voice,
    //                 data3: this.speed,
    //                 data4: this.volume,
    //             },
    //         }).then((response) => {
    //             console.log(response);
    //         });
    //     }
    // },
    download() {
      let str = "/api/tts/download";
      axios
        .get(str, {
          responseType: "blob",
        })
        .then((res) => {
          const name = res.headers["content-disposition"]
            .split("fileName=")[1]
            .replace(/"/g, "");
          const url = window.URL.createObjectURL(new Blob([res.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", name); //or any other extension document.body.appendChild(link); link.click();
          document.body.appendChild(link);
          link.click();
          link.remove();
          console.log(res);
          console.log("다운로드 성공");
        })
        .catch((err) => {
          err;
          console.log("다운로드 실패");
        });
    },
    play(sound) {
      if (sound) {
        var audio = new Audio(sound);
        audio.play();
      }
    },
  },
  // created() {
  //     this.sendData();
  // },
  mounted() {
    this.div_hide();
  },
  computed: {
    isLogin() {
      return this.$store.state.isLogin;
    },
  },
};
</script>

<style>
body {
  margin: 0;
}

textarea {
  resize: none;
}

.tts-bottom-container {
  background: white;
  border-radius: 100px 0px 0px 0px;
}

.tts-ts-container {
  width: 60%;
  margin-left: auto;
  margin-right: auto;
}

.tts-ts-box {
  width: 100%;
  height: 300px;
  margin-top: 10px;
  margin-bottom: 10px;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  padding: 1rem;
  text-align: left;
}

.tts-output-cont {
  width: 100%;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
}

.voice,
.speed,
.volume {
  text-align: center;
  margin-top: 1rem;
  margin-bottom: 1rem;
  margin-right: 0.5rem;
  padding: 10px;
  background-color: white;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
}

.voiceSelect,
.speedSelect,
.volumeSelect {
  border: 1px solid #dbdbdb;
  margin-top: 1rem;
  margin-bottom: 1rem;
  margin-right: 1rem;
  border-radius: 10px;
}

.voiceSelect {
  width: 230px;
  text-align: center;
}

.speedSelect,
.volumeSelect {
  width: 100px;
  text-align: center;
}

.download {
  margin-left: 20px;
}

.download > button {
  height: 100%;
  border: none;
  border-radius: 10px;
  background: #0d66ff;
  color: white;
}

.tts-output-box {
  display: flex;
}

audio::-webkit-media-controls-panel {
  background-color: #0d66ff;
}

audio::-webkit-media-controls-play-button {
  background-color: white;
  border-radius: 50%;
}

.tts-change-btn > button {
  width: 100%;
  height: 60%;
  margin-top: 1rem;
  border: none;
  border-radius: 10px;
  background: #0d66ff;
  color: white;
}

.tts-save-btn > button {
  width: 200px;
  height: 54px;
  border: 1px solid #0d66ff;
  border-radius: 10px;
  background: white;
  color: #0d66ff;
}
</style>
