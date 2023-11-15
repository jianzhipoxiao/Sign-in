function formatDate(time, format = 'YY-MM-DD hh:mm:ss') {
    var date = new Date(time);

    var year = date.getFullYear(),
        month = date.getMonth() + 1, //月份是从0开始的
        day = date.getDate(),
        hour = date.getHours(),
        min = date.getMinutes(),
        sec = date.getSeconds();
    var preArr = Array.apply(null, Array(10)).map(function(elem, index) {
        return '0' + index;
    });
    // 开个长度为10的数组 格式为 00 01 02 03

    var newTime = format.replace(/YY/g, year)
        .replace(/MM/g, preArr[month] || month)
        .replace(/DD/g, preArr[day] || day)
        .replace(/hh/g, preArr[hour] || hour)
        .replace(/mm/g, preArr[min] || min)
        .replace(/ss/g, preArr[sec] || sec);

    return newTime;
}
//2017-05-12 10:05:44
// formatDate(new Date().getTime(), 'YY年MM月DD日'); //2017年05月12日
// formatDate(new Date().getTime(), '今天是YY/MM/DD hh:mm:ss'); //今天是2017/05/12 10:07:45
const vm = new Vue({
    el: "#recoderFrom",
    data() {
        return {
            token: document.cookie,
            place: null,
            type: null,
            key: null,
            users: [],
            userImgSrc: "../images/"
        }
    },

    mounted() {
        axios.get("http://127.0.0.1:8080/Sign/getRomeOnlineUsers")
            .then(res => {
                // console.log(res.data.data)
                if (res.status == 200) {
                    this.users = res.data.data;
                }

            })
            .catch(err => {
                console.error(err);
            })
    },
    methods: {
        siginIn(e) {
            //token 验证
            if (this.token == null) {
                if (confirm("请先登录哦~")) {
                    setInterval(function() {
                        window.location.href = "http://127.0.0.1:8080/pages/login.html";
                    }, 1000)
                }
            }
            if (vm.type == null) {} else {
                axios.post("http://127.0.0.1:8080/Sign/sginIn", {
                        user: 1,
                        inTime: formatDate(new Date().getTime()),
                        place: vm.place,
                        type: vm.type,
                        key: vm.key
                    }, {
                        headers: {
                            'token': document.cookie,

                        }
                    })
                    .then(res => {
                        console.log(res)
                        if (res.status == 200) {
                            axios.get("http://127.0.0.1:8080/Sign/getRomeOnlineUsers")
                                .then(res => {
                                    //签到成功重新请求签到表记录
                                    // console.log(res.data.data)
                                    if (res.status == 200) {
                                        this.users = res.data.data;
                                    }
                                    alert("你的签到已经成功了~")
                                })
                                .catch(err => {
                                    console.error(err);
                                })
                        }
                    })
                    .catch(err => {
                        console.error(err);
                    })
            }

        },
        signOut(e) {
            //token 验证
            if (this.token == null) {
                if (confirm("请先登录哦~")) {
                    setInterval(function() {
                        window.location.href = "http://127.0.0.1:8080/pages/login.html";
                    }, 1000)
                }
            }
            if (vm.key == null) {} else {

                axios.post("http://127.0.0.1:8080/Sign/sginOut", {
                        ouTime: formatDate(new Date().getTime()),
                        place: vm.place,
                        key: vm.key
                    }, {
                        headers: {
                            'token': document.cookie,
                            'Access-Control-Allow-Origin': '*'
                        }
                    })
                    .then(res => {
                        console.log(res)
                        debugger
                        if (res.status == 200) {
                            axios.get("http://127.0.0.1:8080/Sign/getRomeOnlineUsers")
                                .then(res => {
                                    //签到成功重新请求签到表记录
                                    // console.log(res.data.data)
                                    if (res.status == 200) {
                                        this.users = res.data.data;
                                    }
                                    alert("你的签到已经签出~")
                                })
                                .catch(err => {
                                    console.error(err);
                                })
                        }
                    })
                    .catch(err => {
                        console.error(err);
                    })
            }

        }

    },

})

function getLocation() {
    // 创建百度地理位置实例，代替 navigator.geolocation
    var geolocation = new BMap.Geolocation();
    alert("请稍微等待一下哦~")
    geolocation.getCurrentPosition(function(e) {
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            // 百度 geolocation 的经纬度属性不同，此处是 point.lat 而不是 coords.latitude
            vm.place = e.point.lat + ',' + e.point.lng;
            alert("好啦，你可以签到啦~")
        } else {
            vm.place = 'failed' + this.getStatus();
        }
    });
    console.log(vm.place);
}