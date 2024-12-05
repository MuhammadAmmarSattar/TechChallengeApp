package com.muhammad.myapplication.forvia.presentation.app_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muhammad.myapplication.R
import com.muhammad.myapplication.forvia.presentation.custom_components.CustomText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogBottomSheet(
    onDismiss: () -> Unit,

) {

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
//        containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        BottomSheetBody()
    }

}

@Composable
fun BottomSheetBody(){

    Surface(modifier = Modifier.wrapContentSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(text = stringResource(R.string.download_functionality_is_not_available_in_demo_mode),
                textStyle = MaterialTheme.typography.bodyMedium,
                fontSize = 12,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier.padding(15.dp)
                )
        }


        }

}