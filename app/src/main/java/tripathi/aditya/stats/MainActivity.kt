package tripathi.aditya.stats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
//import java.awt.Event.DOWN
import java.awt.*
import java.math.RoundingMode
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    internal lateinit var data: EditText
    internal lateinit var dataselect: Switch
    internal lateinit var dataf: EditText
    internal lateinit var formulas: TextView
    internal lateinit var mean: ToggleButton
    internal lateinit var median: ToggleButton
    internal lateinit var mode: ToggleButton
    internal lateinit var resetResult: Button
    internal lateinit var viewresult: Button
    var checkMean =false
    var checkMedian =false
    var checkMode =false
    var groupedData=false
    var fmean=0.00
    var fmedian=0.00
    var fmode=0.00
    var clearResult=false
    var cData= listOf<String>()
    var freqData= listOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mean = findViewById(R.id.mean) as ToggleButton
        median = findViewById(R.id.median) as ToggleButton
        mode = findViewById(R.id.mode) as ToggleButton
        resetResult = findViewById(R.id.resetResult) as Button
        viewresult = findViewById(R.id.viewresult) as Button
        data = findViewById(R.id.data) as EditText
        dataf = findViewById(R.id.dataf) as EditText
        dataselect = findViewById(R.id.dataselect) as Switch
        formulas = findViewById(R.id.formulas) as TextView
        val temp= """Formulas 
Mean x =∑fx/n
Median M = L+(n/2 -cf)/f ⋅c
Mode Z = L+((f1-f0)/(2⋅f1-f0-f2))⋅c"""
        formulas.setText(temp);
        checkData();
        calculateMean();
        calculateMedian();
        calculateMode();
        resetResultValue();
        viewResult();
    }

    private fun checkData(){
        dataselect.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{buttonView, isChecked ->
                if(isChecked){
                    groupedData=true
                    Toast.makeText(applicationContext,"Grouped",Toast.LENGTH_SHORT).show()
                }else{
                    groupedData=false
                    Toast.makeText(applicationContext,"Un - Grouped",Toast.LENGTH_SHORT).show()
                }
        })
    }

    private fun resetResultValue() {
        resetResult.setOnClickListener(View.OnClickListener {
            if(data.getText().isNotEmpty()){
                data.setText("")
                dataf.setText("")
                //calculate.setText("Calculated values will display here")
                this.clearResult=false
                Toast.makeText(applicationContext,"Result reset =false",Toast.LENGTH_SHORT).show()
            }else{
                data.setText("")
                dataf.setText("")
                this.clearResult=true
                Toast.makeText(applicationContext,"Result reset =true",Toast.LENGTH_SHORT).show()
                //calculate.setText("Calculated values will display here")
            }
        })
    }

    private fun calculateMode() {

        mode.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { // The toggle is enabled
                checkMode=true
                if(data.getText().isNotEmpty()) {
                    if(groupedData){
                        this.cData = data.getText().toString().split(",")
                        val classData = data.getText().toString().split(",", "-")
                        val cData = classData.map { it.toFloat() }

                        var ldata = mutableListOf<Float>()
                        var rdata = mutableListOf<Float>()
                        var k = 0
                        var g = 1
                        var r = 0
                        while (r < cData.size&&k<cData.size) {
                            ldata.add(cData[k])
                            rdata.add(cData[g])
                            g+=2
                            r++
                            k += 2
                        }

                        val freqData = dataf.getText().toString().split(",")
                        this.freqData = freqData
                        var fData = freqData.map { it.toFloat() }

                        var cfx=0.00
                        var N=0.00
                        var j=0
                        var modef =0.00
                        var postion =0
                        var f0=0.00
                        var f2=0.00
                        if(fData.size==1){
                            modef=fData[0].toDouble()
                        }else{
                           /* for(i in fData){
                                if(i.toDouble()>modef){
                                    modef=i.toDouble()
                                    postion=j
                                }
                                j++
                            }*/
                            modef= fData.max()!!.toDouble()
                            postion=fData.indexOf(modef.toFloat())
                            if(postion==0){
                                f0=0.00
                                f2=fData[postion+1].toDouble()
                            }else{
                                f0=fData[postion-1].toDouble()
                                f2=fData[postion+1].toDouble()
                            }
                        }
                        var msize=rdata[0]-ldata[0]
                        var modeff=ldata[postion]+((modef-f0)/(2*(modef-f0-f2))*msize)
                        this.fmode=modeff
                    }else {
                        try {
                            var classData = data.getText().toString().split(",")
                            this.cData=classData
                            var cData = classData.map { it.toFloat() }
                            cData = cData.sorted()
                            var mCount = 0
                            var count = 0
                            var max = 0.00
                            for (i in cData) {
                                count = 0
                                for (j in cData) {
                                    if (j == i)
                                        ++count
                                }
                                if (count > mCount) {
                                    mCount = count;
                                    max = i.toDouble();
                                }

                            }
                            if (max == 0.00) {
                                // calculate.append("\nThere is no Mode")
                            } else {
                                // calculate.append("\nMode is "+max)
                                this.fmode=max
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "Enter Appropriate Values Buddy",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }else{
                    Toast.makeText(applicationContext,"Enter Something Buddy Please",Toast.LENGTH_SHORT).show()
                }

            } else { // The toggle is disabled
                checkMode=false
            }
        })

    }

    private fun calculateMedian() {
        median.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { // The toggle is enabled
                checkMedian=true
                if(data.getText().isNotEmpty()) {
                    if(groupedData){
                        this.cData = data.getText().toString().split(",")
                        val classData = data.getText().toString().split(",", "-")
                        val cData = classData.map { it.toFloat() }

                        var ldata = mutableListOf<Float>()
                        var rdata = mutableListOf<Float>()
                        var k = 0
                        var g = 1
                        var r = 0
                        while (r < cData.size&&k<cData.size) {
                            ldata.add(cData[k])
                            rdata.add(cData[g])
                            g+=2
                            r++
                            k += 2
                        }
                        val middle=ldata.size/2
                        var mllimit=ldata[middle]
                        var mulimit =rdata[middle]
                        var mcf=0.00
                        var mf=0.00
                        val freqData = dataf.getText().toString().split(",")
                        this.freqData = freqData
                        var fData = freqData.map { it.toFloat() }

                        var cfx=0.00
                        var N=0.00
                        var j=0
                        for(i in fData){
                            cfx += i .toDouble()
                            N+=fData[j]
                            if(j==middle){
                                mcf=cfx-i
                                mf= i.toDouble()
                            }
                            j++
                        }
                        var limit=mulimit-mllimit
                        var medianf=mllimit+((limit/mf)*(N/2)-mcf)
                        this.fmedian=medianf
                    }else{
                        try {
                            val classData = data.getText().toString().split(",")
                            this.cData=classData
                            var cData= classData.map{it.toFloat()}
                            cData=cData.sorted()
                            val size=cData.size
                            var valueM =0.00
                            if(size%2==0){
                                valueM=(cData[(size/2)-1]+cData[(size/2)])/2.00
                            }else{
                                valueM= cData[(size/2)].toDouble();
                            }
                            //calculate.append("\nMedian is "+valueM)
                            this.fmedian=valueM
                        }catch (e:Exception){
                            Toast.makeText(applicationContext,"Enter Appropriate Values Buddy",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(applicationContext,"Enter Something Buddy Please",Toast.LENGTH_SHORT).show()
                }

            } else { // The toggle is disabled
                checkMedian=false
            }
        })

    }

    private fun calculateMean() {
      // var fData= listOf<Float>()
     //   var cData= listOf<Float>()
      //  var mData= mutableListOf<Float>()
        mean.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { // The toggle is enabled
                checkMean=true
                if(data.getText().isNotEmpty()) {
                    if(groupedData){
                        try {
                            this.cData = data.getText().toString().split(",")
                            val classData = data.getText().toString().split(",", "-")
                            val cData = classData.map { it.toFloat() }

                                var ldata = mutableListOf<Float>()
                                var rdata = mutableListOf<Float>()
                                var k = 0
                                var g = 1
                                var r = 0
                                while (r < cData.size&&k<cData.size) {
                                    ldata.add(cData[k])
                                    rdata.add(cData[g])
                                    g+=2
                                    r++
                                    k += 2
                                }
                           // Toast.makeText(applicationContext,ldata.toString(),Toast.LENGTH_SHORT).show()
                                var f = 0
                                var mData = mutableListOf<Float>()
                                while (f < ldata.size ) {
                                    mData.add((ldata[f] + rdata[f])/2)
                                    f++
                                }
                                val freqData = dataf.getText().toString().split(",")
                                this.freqData = freqData
                           // Toast.makeText(applicationContext,"TOfloat 1",Toast.LENGTH_SHORT).show()
                                var fData = freqData.map { it.toFloat() }
                            //Toast.makeText(applicationContext,"TOfloat 2",Toast.LENGTH_SHORT).show()




                            var sumfx=0.00
                            var N=0.00
                            var j=0
                            for(i in mData){
                                sumfx += (i * fData[j]).toDouble()
                                N+=fData[j]
                                j++
                            }
                            var fmean=sumfx/N
                            this.fmean=fmean
                            //calculate.append("\nMean is "+avg)
                        }catch (e:Exception){
                            Toast.makeText(applicationContext,"Error final freq",Toast.LENGTH_SHORT).show()
                        }
                    }else if(!groupedData){
                        try{
                            val classData = data.getText().toString().split(",")
                            this.cData=classData
                            val cData= classData.map{it.toFloat()}
                            var fmean=cData.average()
                            this.fmean=fmean
                            //calculate.append("\nMean is "+avg)
                        }catch (e:Exception){
                            Toast.makeText(applicationContext,"Enter Appropriate Values Buddy",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(applicationContext,"Dont Enter frequency in ungrouped data",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(applicationContext,"Enter Something Buddy Please",Toast.LENGTH_SHORT).show()
                }
            } else { // The toggle is disabled
            }
        })
    }

    private fun viewResult() {
        viewresult.setOnClickListener(View.OnClickListener{
            // Intent intent = new Intent(this@MainActivity,result::class.java)
            val intent = Intent(applicationContext, result::class.java)
            intent.putStringArrayListExtra("Interval",ArrayList(cData))
            intent.putExtra("Fre",ArrayList(freqData))
            val df = DecimalFormat("##.##")
            df.setRoundingMode(RoundingMode.DOWN)
            intent.putExtra("Mean",df.format(fmean).toString())
            intent.putExtra("Mode",df.format(fmode).toString())
            intent.putExtra("Median",df.format(fmedian).toString())
            intent.putExtra("cResult",clearResult.toString())
            startActivity(intent)
        })
    }
}
