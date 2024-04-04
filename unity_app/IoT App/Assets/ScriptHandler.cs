using System;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class ScriptHandler : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    public TextMeshProUGUI txtText;
    public Button txtButton;

    private int status = 0;

    public void onButtonClick()
    {
        status = 1 - status;
        txtText.SetText(Convert.ToString(status));
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
