/**
 * 33101_33409 浙江省_衢州市地图
 */
(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['exports', 'echarts'], factory);
    } else if (typeof exports === 'object' && typeof exports.nodeName !== 'string') {
        // CommonJS
        factory(exports, require('echarts'));
    } else {
        // Browser globals
        factory({}, root.echarts);
    }
}(this, function (exports, echarts) {
    var log = function (msg) {
        if (typeof console !== 'undefined') {
            console && console.error && console.error(msg);
        }
    }
    if (!echarts) {
        log('ECharts is not Loaded');
        return;
    }
    if (!echarts.registerMap) {
        log('ECharts Map is not loaded')
        return;
    }
    echarts.registerMap('33409', {"type": "FeatureCollection", "features": [{"type":"Feature","properties":{"name":"江山市","id":"330881"},"geometry":{"type":"MultiPolygon","coordinates":[[[[118.617345,28.863843],[118.605152617188,28.8672658515626],[118.613922148438,28.8760353828125],[118.617345,28.863843]]],[[[118.617345,28.863843],[118.6686340625,28.8725563789063],[118.68170046875,28.88948753125],[118.697345,28.893843],[118.72298953125,28.85948753125],[118.737345,28.833843],[118.74312625,28.81913596875],[118.74154421875,28.8084133125001],[118.773507109375,28.765298078125],[118.79170046875,28.6581984687501],[118.805811796875,28.6473073554688],[118.801607695313,28.6188430000001],[118.80312625,28.60855003125],[118.785553007813,28.563843],[118.797345,28.5338430000001],[118.724986601563,28.5156154609375],[118.721221953125,28.4988430000001],[118.724586210938,28.483843],[118.721221953125,28.468843],[118.724586210938,28.453843],[118.716178007813,28.4163405585938],[118.75142703125,28.4242433906251],[118.728961210938,28.3518630195313],[118.702393828125,28.3335207343751],[118.697345,28.3138430000001],[118.691519804688,28.3096681953125],[118.673170195313,28.2780178046875],[118.595513945313,28.2624489570313],[118.583170195313,28.2796681953126],[118.542115507813,28.2904982734375],[118.530484648438,28.2742702460938],[118.488648710938,28.2825368476563],[118.496851835938,28.2410353828125],[118.438219023438,28.252622296875],[118.427345,28.293843],[118.443077421875,28.2981081367187],[118.47443484375,28.3217018867188],[118.468863554688,28.3540773750001],[118.43865359375,28.4028884101563],[118.47127078125,28.4766286445313],[118.40966921875,28.4988063789063],[118.433077421875,28.5081081367188],[118.441612578125,28.5172634101563],[118.417345,28.523843],[118.4108215625,28.5635451484376],[118.413824492188,28.583843],[118.410362578125,28.6072658515625],[118.423140898438,28.628452375],[118.420865507813,28.643843],[118.425811796875,28.6773073554688],[118.41170046875,28.68819846875],[118.40298953125,28.69948753125],[118.391529570313,28.7083303046875],[118.395362578125,28.7342702460938],[118.377345,28.773843],[118.38298953125,28.7781984687501],[118.39170046875,28.7894875312501],[118.428546171875,28.79819846875],[118.465601835938,28.7758473945313],[118.49892703125,28.8315651679688],[118.522857695313,28.8280275703125],[118.531832304688,28.8396584296876],[118.542345,28.8381032539063],[118.604576445313,28.84730003125],[118.617345,28.863843]]]]}},{"type":"Feature","properties":{"name":"常山县","id":"330822"},"geometry":{"type":"MultiPolygon","coordinates":[[[[118.617345,28.863843],[118.613922148438,28.8760353828125],[118.605152617188,28.8672658515626],[118.604576445313,28.84730003125],[118.542345,28.8381032539063],[118.531832304688,28.8396584296876],[118.522857695313,28.8280275703125],[118.49892703125,28.8315651679688],[118.465601835938,28.7758473945313],[118.428546171875,28.79819846875],[118.39170046875,28.7894875312501],[118.38298953125,28.7781984687501],[118.377345,28.773843],[118.36732546875,28.8079787421875],[118.290972929688,28.83038596875],[118.292882109375,28.849126203125],[118.263043242188,28.9191091132813],[118.257345,28.9238430000001],[118.262857695313,28.9309841132813],[118.297345,28.9258864570313],[118.353765898438,28.9342263007813],[118.34990359375,28.9603786445313],[118.379210234375,28.9830007148438],[118.421832304688,28.9767018867188],[118.43170046875,28.98948753125],[118.443160429688,28.9983303046875],[118.441607695313,29.008843],[118.444205351563,29.0264186835938],[118.47298953125,29.0381984687501],[118.517701445313,29.0713503242188],[118.53170046875,29.08948753125],[118.571646757813,29.1133766914063],[118.58170046875,29.14948753125],[118.6113684375,29.1723879218751],[118.617345,29.193843],[118.666519804688,29.2019216132813],[118.708800078125,29.1956716132813],[118.73170046875,29.2094875312501],[118.747345,29.213843],[118.757345,29.193843],[118.75298953125,29.17819846875],[118.74170046875,29.15948753125],[118.73298953125,29.12819846875],[118.701485625,29.0857106757813],[118.686226835938,28.9824269843751],[118.71093875,28.9633498359376],[118.716749296875,28.9240505195313],[118.697345,28.893843],[118.68170046875,28.88948753125],[118.6686340625,28.8725563789063],[118.617345,28.863843]]]]}},{"type":"Feature","properties":{"name":"开化县","id":"330824"},"geometry":{"type":"MultiPolygon","coordinates":[[[[118.097345,29.003843],[118.107345,29.003843],[118.107345,28.9938430000001],[118.097345,28.9938430000001],[118.097345,29.003843]]],[[[118.617345,29.283843],[118.629537382813,29.2804201484376],[118.620767851563,29.2716506171875],[118.617345,29.283843]]],[[[118.617345,29.283843],[118.610704375,29.2804836250001],[118.603985625,29.2306008125001],[118.6292590625,29.2178151679688],[118.617345,29.193843],[118.6113684375,29.1723879218751],[118.58170046875,29.14948753125],[118.571646757813,29.1133766914063],[118.53170046875,29.08948753125],[118.517701445313,29.0713503242188],[118.47298953125,29.0381984687501],[118.444205351563,29.0264186835938],[118.441607695313,29.008843],[118.443160429688,28.9983303046875],[118.43170046875,28.98948753125],[118.421832304688,28.9767018867188],[118.379210234375,28.9830007148438],[118.34990359375,28.9603786445313],[118.353765898438,28.9342263007813],[118.297345,28.9258864570313],[118.262857695313,28.9309841132813],[118.257345,28.9238430000001],[118.231793242188,28.9193971992188],[118.196920195313,28.9059914375],[118.182896757813,28.9122731757813],[118.22107546875,28.94284690625],[118.176373320313,28.9786452460938],[118.120709257813,28.990688703125],[118.12302859375,29.0093581367188],[118.107345,29.013843],[118.097345,29.013843],[118.097345,29.003843],[118.091519804688,29.0080178046875],[118.083170195313,29.0296681953125],[118.071519804688,29.0480178046875],[118.063170195313,29.0796681953126],[118.039014921875,29.0969826484375],[118.043350859375,29.1189235664063],[118.023189726563,29.2025978828125],[118.063170195313,29.2180178046875],[118.074361601563,29.2336330390625],[118.070367460938,29.253843],[118.075186796875,29.2782228828126],[118.067345,29.283843],[118.072799101563,29.2906520820313],[118.092345,29.2882204414063],[118.112345,29.290708234375],[118.132345,29.2882204414063],[118.148428984375,29.2902223945313],[118.203023710938,29.3583962226563],[118.201724882813,29.368843],[118.203023710938,29.3792971015625],[118.191793242188,29.3882888007812],[118.187345,29.393843],[118.1986340625,29.4025563789062],[118.216549101563,29.4257692695313],[118.242345,29.4295827460938],[118.252345,29.4281032539063],[118.262345,29.4295827460938],[118.272345,29.4281032539063],[118.282345,29.4295827460938],[118.310987578125,29.4253493476563],[118.300494414063,29.4963527656251],[118.322891875,29.4996633125],[118.337345,29.473843],[118.345694609375,29.4621926093751],[118.363170195313,29.4496681953125],[118.371519804688,29.4380178046876],[118.403463164063,29.419497296875],[118.401236601563,29.4082228828125],[118.413170195313,29.3996681953125],[118.4227746875,29.3862673164062],[118.45666140625,29.3647560859375],[118.485303984375,29.3704152656251],[118.513170195313,29.3596681953125],[118.521519804688,29.3480178046875],[118.590816679688,29.3341237617187],[118.617345,29.283843]]]]}},{"type":"Feature","properties":{"name":"柯城区","id":"330802"},"geometry":{"type":"MultiPolygon","coordinates":[[[[118.757345,29.193843],[118.773902617188,29.1877638984375],[118.799034453125,29.1897829414063],[118.8227746875,29.1791896796875],[118.821734648438,29.1662380195313],[118.853980742188,29.0939723945313],[118.884273710938,29.0678713203125],[118.8819153125,29.0385182929687],[118.893150664063,29.0288430000001],[118.87271609375,29.0112404609375],[118.882042265625,28.9782228828125],[118.920445585938,28.9923268867187],[118.896666289063,28.9496999335938],[118.912345,28.9484401679688],[118.922691679688,28.9492726875001],[118.93197390625,28.92847190625],[118.946763945313,28.9157277656251],[118.9318371875,28.888969953125],[118.945377226563,28.83948753125],[118.922345,28.8376369453126],[118.902022734375,28.8392702460938],[118.892345,28.8280397773438],[118.868834257813,28.8553298164063],[118.833819609375,28.8854958320313],[118.831944609375,28.908843],[118.833521757813,28.92847190625],[118.77197390625,28.87921409375],[118.76271609375,28.8584719062501],[118.75197390625,28.84921409375],[118.74271609375,28.83847190625],[118.737345,28.833843],[118.72298953125,28.85948753125],[118.697345,28.893843],[118.716749296875,28.9240505195313],[118.71093875,28.9633498359376],[118.686226835938,28.9824269843751],[118.701485625,29.0857106757813],[118.73298953125,29.12819846875],[118.74170046875,29.15948753125],[118.75298953125,29.17819846875],[118.757345,29.193843]]]]}},{"type":"Feature","properties":{"name":"龙游县","id":"330825"},"geometry":{"type":"MultiPolygon","coordinates":[[[[119.327345,28.8438430000001],[119.330767851563,28.8560353828126],[119.339537382813,28.8472658515625],[119.327345,28.8438430000001]]],[[[119.327345,28.8438430000001],[119.337252226563,28.8239138007813],[119.317345,28.813843],[119.292506132813,28.8082009101563],[119.280079375,28.8097463203126],[119.262896757813,28.7882888007813],[119.251793242188,28.7793971992188],[119.240987578125,28.7659035468751],[119.186319609375,28.7727028632813],[119.152896757813,28.7482888007813],[119.0830871875,28.738130109375],[119.072061796875,28.7394997382813],[119.057345,28.733843],[119.061793242188,28.7493971992188],[119.073155546875,28.7687282539063],[119.06088015625,28.80065940625],[119.094888945313,28.8278932929688],[119.111793242188,28.9342653632813],[119.057369414063,28.97784690625],[119.087579375,29.0020388007813],[119.043316679688,29.0716115546875],[119.085391875,29.0893410468751],[119.078204375,29.1471388984375],[119.051793242188,29.1682888007813],[119.037345,29.2238430000001],[119.062301054688,29.2299733710938],[119.072388945313,29.2277126289063],[119.122994414063,29.2401442695313],[119.13142703125,29.2279274726562],[119.16326296875,29.2197585273438],[119.182901640625,29.206968],[119.191573515625,29.2301369453125],[119.21142703125,29.2256862617188],[119.20326296875,29.2497585273438],[119.185982695313,29.276294171875],[119.21060671875,29.2818166328125],[119.24142703125,29.2579274726563],[119.257345,29.253843],[119.224117460938,29.2084914375],[119.251558867188,29.1469850898438],[119.3021496875,29.0882643867188],[119.317345,29.093843],[119.3258215625,29.07179221875],[119.319234648438,29.018843],[119.323248320313,28.986577375],[119.298961210938,28.9671291328126],[119.341793242188,28.8942653632813],[119.322803984375,28.8681642890625],[119.302896757813,28.870639875],[119.315181914063,28.8535842109375],[119.327345,28.8438430000001]]]]}},{"type":"Feature","properties":{"name":"衢江区","id":"330803"},"geometry":{"type":"MultiPolygon","coordinates":[[[[118.907345,29.3338430000001],[118.914078398438,29.3205300117188],[118.9552746875,29.2998366523438],[118.945504179688,29.2798342109375],[118.97287234375,29.2659865546875],[118.980758085938,29.2269435859376],[119.012447539063,29.2114675117188],[119.037345,29.2238430000001],[119.051793242188,29.1682888007813],[119.078204375,29.1471388984375],[119.085391875,29.0893410468751],[119.043316679688,29.0716115546875],[119.087579375,29.0020388007813],[119.057369414063,28.97784690625],[119.111793242188,28.9342653632813],[119.094888945313,28.8278932929688],[119.06088015625,28.80065940625],[119.073155546875,28.7687282539063],[119.061793242188,28.7493971992188],[119.057345,28.733843],[119.021519804688,28.7096681953125],[118.958595,28.6539650703125],[118.917198515625,28.63800315625],[118.90181765625,28.5612721992188],[118.874674101563,28.5196681953125],[118.824425078125,28.5515700507813],[118.797345,28.5338430000001],[118.785553007813,28.563843],[118.80312625,28.60855003125],[118.801607695313,28.6188430000001],[118.805811796875,28.6473073554688],[118.79170046875,28.6581984687501],[118.773507109375,28.765298078125],[118.74154421875,28.8084133125001],[118.74312625,28.81913596875],[118.737345,28.833843],[118.74271609375,28.83847190625],[118.75197390625,28.84921409375],[118.76271609375,28.8584719062501],[118.77197390625,28.87921409375],[118.833521757813,28.92847190625],[118.831944609375,28.908843],[118.833819609375,28.8854958320313],[118.868834257813,28.8553298164063],[118.892345,28.8280397773438],[118.902022734375,28.8392702460938],[118.922345,28.8376369453126],[118.945377226563,28.83948753125],[118.9318371875,28.888969953125],[118.946763945313,28.9157277656251],[118.93197390625,28.92847190625],[118.922691679688,28.9492726875001],[118.912345,28.9484401679688],[118.896666289063,28.9496999335938],[118.920445585938,28.9923268867187],[118.882042265625,28.9782228828125],[118.87271609375,29.0112404609375],[118.893150664063,29.0288430000001],[118.8819153125,29.0385182929687],[118.884273710938,29.0678713203125],[118.853980742188,29.0939723945313],[118.821734648438,29.1662380195313],[118.8227746875,29.1791896796875],[118.799034453125,29.1897829414063],[118.773902617188,29.1877638984375],[118.757345,29.193843],[118.747345,29.213843],[118.75326296875,29.2179274726563],[118.76142703125,29.2497585273438],[118.77326296875,29.2579274726563],[118.78377078125,29.2731520820312],[118.82326296875,29.2879274726563],[118.871803007813,29.3300856757813],[118.882388945313,29.3277126289063],[118.907345,29.3338430000001]]]]}}]});
}));